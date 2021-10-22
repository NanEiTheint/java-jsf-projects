package com.mmit.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import com.mmit.entity.Category;
import com.mmit.entity.Product;
import com.mmit.services.CategoryService;

@Named
@ViewScoped
public class CategoryBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@EJB
	private CategoryService service;
	private Category category;
	private List<Category> categoryList;
	private Part imgPart;
	
	private ServletContext context;
	private String imgFolder=null;
	
	
	@PostConstruct
	public void init()
	{
		category=new Category();
		categoryList=service.findAll();
		
		//photo
		context=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		imgFolder=context.getRealPath("/resources/upload");
	}
	//save
	public String save()
	{
		try {
				
				if(imgPart != null)
				{
					String photoName=getPhotoName(imgPart.getSubmittedFileName());
					
					//Edit product(delete old photo)
					if(category.getId()!=0)
					{
						Category oldCategory=service.findById(category.getId());
						String oldPhoto=oldCategory.getPhoto();
						if(oldPhoto!=null)
						{
							File oldFile=new File(imgFolder+File.separator+oldPhoto);
							if(oldFile.exists())
							{
								oldFile.delete();
							}
						}
						
					}
					
					imgPart.write(imgFolder+File.separator+photoName);//save image
					
					category.setPhoto(photoName);
					
				}
				//imgPart is null and not update photo
				else
				{
					if(category.getId()!=0)
					{
						category.setPhoto(service.findById(category.getId()).getPhoto());
					}
					
				}
				
				service.save(category);
				
				return "/views/categories.xhtml?faces-redirect=true";
			} catch (EJBException e) {
				FacesContext cxt=FacesContext.getCurrentInstance();
				cxt.addMessage("editForm:name", new FacesMessage("Category name already exist!"));
			}
			catch (IOException e) {
				
			}
		
		return null;
	}
	

	//Ajax method
	public void getCategoryInfo(int id)
	{
		if(id==0)
			category=new Category();
		else
			category=service.findById(id);
	}
	
	//change img name
	private String getPhotoName(String subName) {
		//oldName="user.jpg";
		String tmpName=subName.substring(0, subName.lastIndexOf("."));
		String newName="img"+System.currentTimeMillis();
		subName=subName.replace(tmpName, newName);
		
		return subName;
	}
	
	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public Part getImgPart() {
		return imgPart;
	}
	public void setImgPart(Part imgPart) {
		this.imgPart = imgPart;
	}
	
	
	
	
}
