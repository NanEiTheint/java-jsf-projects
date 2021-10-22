package com.mmit.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.OrderDetail;
import com.mmit.entity.Orders;
import com.mmit.entity.Product;
import com.mmit.services.ProductService;

@Named
@SessionScoped
public class CartBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Orders order;//same cart entity
	@Inject
	private ProductService service;
	
	@PostConstruct
	private void init()
	{
		order=new Orders();
	}
	
	//count
	public int getItemCount()
	{
		return order.getDetails().size();
	}
	
	//update total ajax
	public void updateQty()
	{
		order.getDetails().forEach(od->System.out.println(String.format("%s,%d", od.getSubQty())));
	}
	
	//remove cart
	public String removeCart(OrderDetail od)
	{
		order.getDetails().remove(od);
		return "/public/cart-page.xhtml?faces-redirect=true";
	}
	
	//remove cart
	public void removeCart(int productId)
	{
		for(OrderDetail od: order.getDetails() )
		{
			if(od.getProduct().getId()==productId)
			{
				
				return;
			}
			
		}
	}
	//ajax
	public void addToCart(int pId)
	{
		
		//check product already exit in cart
		for(OrderDetail od: order.getDetails() )
		{
			if(od.getProduct().getId()==pId)
			{
				od.setSubQty(od.getSubQty()+1);
				return;
			}
			
		}
		//new product to cart
		Product p=service.findById(pId);
		OrderDetail pd=new OrderDetail();
		pd.setProduct(p);
		pd.setSubQty(1);
		order.addOrderItem(pd);
		
	}
	
	

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}
	

}
