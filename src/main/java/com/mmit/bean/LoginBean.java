package com.mmit.bean;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

import com.mmit.entity.Users;
import com.mmit.security.AppException;

@Named
@SessionScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Require LoginId")
	private String loginId;
	@NotEmpty(message = "Require Password")
	private String password;
	
	@Inject
	private ExternalContext exContext;
	@Inject
	private SecurityContext securityContext;
	
	private Users loginUser;
	@PostConstruct
	private void init()
	{
		loginUser=new Users();
	}
	
	public String Login()
	{
		try {
			HttpServletRequest request=(HttpServletRequest) exContext.getRequest();
			HttpServletResponse response=(HttpServletResponse) exContext.getResponse();
			
			UsernamePasswordCredential credential=new UsernamePasswordCredential(loginId, password);
			AuthenticationStatus status=securityContext.authenticate(request, response, AuthenticationParameters.withParams().credential(credential));
			
			if(status==AuthenticationStatus.SUCCESS)
			{
				if(loginUser.getRole().name().equals("Customer"))
				{
					return "index.xhtml?faces-redirect=true";
				}
				return "/views/products.xhtml?faces-redirect=true";
			}
			
		} catch (AppException e) {
			FacesContext cxt=FacesContext.getCurrentInstance();
			cxt.addMessage(null, new FacesMessage(e.getMessage()));//to show user login error message to login page
		}
		return null;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public Users getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Users loginUser) {
		this.loginUser = loginUser;
	}

	
}
