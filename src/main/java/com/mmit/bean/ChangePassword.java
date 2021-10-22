package com.mmit.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.security.AppException;
import com.mmit.services.UserService;

@Named
@RequestScoped
public class ChangePassword {
	
	private String oldPasswrod;
	private String newPasswrod;
	private String confirmPassword;
	@Inject
	private UserService service; 
	
	public String saveChange() 
	{
		try {
			service.saveChangePassword(oldPasswrod,newPasswrod,confirmPassword);
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();//destory current session and go login after logout
			return "/views/users.xhtml?faces-redirect=true";
			
		} catch (AppException e) {
			FacesContext context=FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
		
	}
	
	public String getOldPasswrod() {
		return oldPasswrod;
	}
	public void setOldPasswrod(String oldPasswrod) {
		this.oldPasswrod = oldPasswrod;
	}
	public String getNewPasswrod() {
		return newPasswrod;
	}
	public void setNewPasswrod(String newPasswrod) {
		this.newPasswrod = newPasswrod;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	

}
