package com.mmit.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Category;
import com.mmit.services.CategoryService;

@Named
public class CategoryConverter implements Converter<Category>{
	
	@Inject
	private CategoryService service;

	@Override
	public Category getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null)
		{
			return service.findById(Integer.parseInt(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Category value) {
		if(value != null)
		{
			return String.valueOf(value.getId());
		}
		return null;
	}

}
