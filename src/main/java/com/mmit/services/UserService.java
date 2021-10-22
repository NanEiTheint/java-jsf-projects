package com.mmit.services;

import java.security.Principal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.mmit.entity.Users;
import com.mmit.entity.Users.Role;
import com.mmit.security.AppException;

@Stateless
public class UserService {
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private Pbkdf2PasswordHash encoder;
	@Inject
	Principal principal;
	
	

	public void create(Users user) {
		if(user.getId()==0)
		{
			if(user.getRole()==null)
			{
				user.setRole(Role.Customer);
			}
			user.setPassword(encoder.generate(user.getPassword().toCharArray()));
			em.persist(user);
		}
		else
		{
			em.merge(user);
		}

	}

	public List<Users> findAll() {
		TypedQuery<Users> query=em.createNamedQuery("findAllUser", Users.class);
		query.setParameter("email", principal.getName());//return loginId(email)
		return query.getResultList();
	}

	public Users findByLoginId(String email) {
		TypedQuery<Users> query=em.createNamedQuery("findByLoginIdUser", Users.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
		}
		return null;
	}
	public long getCount() {
		// TODO Auto-generated method stub
		TypedQuery<Long> query=em.createNamedQuery("countUsers", Long.class);
		return query.getSingleResult();
	}

	public void saveChangePassword(String oldPasswrod, String newPasswrod, String confirmPassword) {
		Users loginUser=findByLoginId(principal.getName());
		if(!encoder.verify(oldPasswrod.toCharArray(), loginUser.getPassword()))
		{
			//not match password
			//call custom exception
			throw new AppException("Incorrect old password!");
		}
		if(!newPasswrod.equals(confirmPassword))
		{
			throw new AppException("Confirm password don not match!");
		}
		
		loginUser.setPassword(encoder.generate(newPasswrod.toCharArray()));
	}

	public void deleteUser(int id) {
		Users u=em.find(Users.class, id);
		em.remove(u);
		
	}

	public Users findById(String id) {
		TypedQuery<Users> query=em.createNamedQuery("findByIdUsers", Users.class);
		query.setParameter("id",Integer.parseInt(id));
		return query.getSingleResult();
	}

}
