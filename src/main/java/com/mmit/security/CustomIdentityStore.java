package com.mmit.security;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Users;
import com.mmit.services.UserService;

@ApplicationScoped
public class CustomIdentityStore implements IdentityStore{
	
	@Inject
	private UserService service;
	
	@Inject
	private Pbkdf2PasswordHash encoder;
	
	@Inject
	private LoginBean loginBean;
	
	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential nameAndpassword=(UsernamePasswordCredential) credential;
		
		Users user=service.findByLoginId(nameAndpassword.getCaller());//return login id
		if(user == null)
		{
			throw new AppException("Incorrect Login Id!");
		}
		if(!encoder.verify(nameAndpassword.getPassword().getValue(), user.getPassword()))
		{
			throw new AppException("Incorrect Password!");
		}
		loginBean.setLoginUser(user);
		return new CredentialValidationResult(user.getEmail(), Set.of(user.getRole().name()));
		
	}
	
}
