package com.mmit.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import com.mmit.entity.Category;
import com.mmit.entity.Product;
import com.mmit.services.ProductService;

@Named
@ViewScoped
public class ProductBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<Product> productList;
	@Inject
	private ProductService service;
	private Product product;
	private Part imgPart;
	private Part uploadFile;
	
	@Inject
	private LoginBean loginBean;
	
	private ServletContext context;
	private String imgFolder=null;
	
	private String catName;
	private String brandName;
	
	
	
	@PostConstruct
	private void init()
	{
		String id=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
		if(id!=null)
		{
			product=service.findById(Integer.parseInt(id));
			
		}
		else
			product=new Product();
		
		//photo
		context=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		imgFolder=context.getRealPath("/resources/upload");
		
		productList=service.findAll();
	
		
		//filter by category
		String catId=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("catId");
		if(catId!=null)
		{
			productList=service.filterByCategoryId(Integer.parseInt(catId));
			catName=service.findCatNameByCatId(Integer.parseInt(catId));
			
		}
		
		//filter by brands
		String bId=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("brandId");
		if(bId!=null)
		{
			productList=service.filterByBrand(Integer.parseInt(bId));
			brandName=service.findBrandNameByBrandId(Integer.parseInt(bId));
		}
	}
	//save
	public String saveProduct()
	{
		try {
			
			if(imgPart != null)
			{
				String photoName=getPhotoName(imgPart.getSubmittedFileName());
				
				//Edit product(delete old photo)
				if(product.getId()!=0)
				{
					Product oldProduct=service.findById(product.getId());
					String oldPhoto=oldProduct.getPhoto();
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
				
				product.setPhoto(photoName);
				
			}
			//imgPart is null and not update photo
			else
			{
				if(product.getId()!=0)
				{
					product.setPhoto(service.findById(product.getId()).getPhoto());
				}
				
			}
			
			service.save(product);
			
			return "/views/products.xhtml?faces-redirect=true";
		} catch (EJBException e) {
			FacesContext cxt=FacesContext.getCurrentInstance();
			cxt.addMessage("createProduct:name", new FacesMessage("Product name already exist!"));
		}
		catch (IOException e) {
			
		}
		
		return null;
	}
	//delete
	public String removeProduct(int id)
	{
		Product p=service.findById(id);
		if(p.getPhoto()!=null)
		{
			File oldFile=new File(imgFolder+File.separator+p.getPhoto());
			if(oldFile.exists())
			{
				oldFile.delete();
			}
		}
		service.deleteProduct(id);
		return "/views/products.xhtml? faces-redirect=true";
	}
	
	//product detail ajax
	public void detailProduct(int pId)
	{
		product=service.findById(pId);
	}
	
	//upload file part
	public String upload()
	{
		if(uploadFile !=null || uploadFile.getSubmittedFileName().equals(""))
		{
			service.uploadData(uploadFile);
		}
		return "/views/products.xhtml?faces-redirect=true";
	}

	//change img name
	private String getPhotoName(String subName) {
		//oldName="user.jpg";
		String tmpName=subName.substring(0, subName.lastIndexOf("."));
		String newName="img"+System.currentTimeMillis();
		subName=subName.replace(tmpName, newName);
		
		return subName;
	}
	
	
	
	
	

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Part getImgPart() {
		return imgPart;
	}

	public void setImgPart(Part imgPart) {
		this.imgPart = imgPart;
	}
	public String getCatName() {
		return catName;
	}
	public String getBrandName() {
		return brandName;
	}
	public Part getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(Part uploadFile) {
		this.uploadFile = uploadFile;
	}
	
}
