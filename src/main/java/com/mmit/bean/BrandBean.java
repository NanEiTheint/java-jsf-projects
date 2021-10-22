package com.mmit.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mmit.entity.Brand;
import com.mmit.services.BrandService;

@Named
@ViewScoped
public class BrandBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@EJB
	private BrandService service;
	private Brand brand;
	private List<Brand> brandList;
	
	@PostConstruct
	public void init() 
	{
		brand=new Brand();
		brandList=service.findAll();
	}
	
	public String saveBrand()
	{
		service.save(brand);
		return "/views/brands.xhtml?faces-redirect=true";
	}
	
	//AJax method
	public void editBrand(int id)
	{
		brand=service.findById(id);
	}
	

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
	
	

}
