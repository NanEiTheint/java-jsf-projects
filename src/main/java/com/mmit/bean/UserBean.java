package com.mmit.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Users;
import com.mmit.entity.Users.Role;
import com.mmit.services.UserService;


@Named
@ViewScoped
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Users user;
	@Inject
	private UserService service;
	private List<Users> userList;
	@Inject
	private LoginBean loginBean;
	
	
	
	@PostConstruct
	public void init()
	{
		String id=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if(id!=null)
			user=service.findById(id);
		else
			user=new Users();
		
		userList=service.findAll();
	}
	
	public void checkEmail()
	{
		Users obj=service.findByLoginId(user.getEmail());
		if(obj!=null && user.getId()==0)
		{
			FacesContext content=FacesContext.getCurrentInstance();
			content.addMessage("create:email", new FacesMessage(obj.getEmail()+" already exist."));
			content.validationFailed();
		}
	}

	public String createUser()
	{
		FacesContext context=FacesContext.getCurrentInstance();
		if(context.isValidationFailed())
			return null;
		else
		{
			
			service.create(user);
		}
		if(user.getRole().equals(Role.Customer))
		{
			loginBean.setLoginUser(user);//show edit data  when edit 
			return "/index.xhtml?faces-redirect=true";
			
		}
		else
		{
			loginBean.setLoginUser(user);//show edit data when edit
			return "/views/users.xhtml?faces-redirect=true";
		}
			
		
	}
	//ajax method
	public void findUser(String email)
	{
		user=service.findByLoginId(email);
	}
	
	public String removeUser()
	{
		service.deleteUser(user.getId());
		return "/views/users.xhtml?faces-redirect=true";
	}
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

}
