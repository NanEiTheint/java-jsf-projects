package com.mmit.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Brand;
import com.mmit.services.BrandService;

@Named
public class BrandConverter implements Converter<Brand>{
	
	@Inject
	private BrandService service;

	@Override
	public Brand getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null)
		{
			return service.findById(Integer.parseInt(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Brand value) {
		if(value != null)
		{
			return String.valueOf(value.getId());
		}
		return null;
	}

}
