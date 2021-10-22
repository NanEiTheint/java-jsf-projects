package com.mmit.security;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mmit.entity.Users;
import com.mmit.entity.Users.Role;
import com.mmit.services.UserService;


@ApplicationScoped
@Singleton
@Startup
public class CreateAdminUser {

	@Inject
	private UserService service;
	
	@PostConstruct
	private void init()
	{
		long userCount=service.getCount();
		if(userCount==0)
		{
			
				Users user=new Users();
				user.setUserName("NEET");
				user.setEmail("neet@gmail.com");
				user.setPassword("123");
				user.setAddress("Magway");
				user.setPhone("09213455665");
				user.setRole(Role.Admin);
				service.create(user);
				
			}
	}

}
