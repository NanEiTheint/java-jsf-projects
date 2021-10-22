package com.mmit.bean;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Orders;
import com.mmit.entity.Orders.Status;
import com.mmit.services.OrderService;

@RequestScoped
@Named
public class OrderBean {
	@Inject
	private OrderService service;
	private Orders order;
	private List<Orders> orderList;
	
	@PostConstruct
	private void init()
	{
		order=new Orders();
		orderList=service.findAll();
	}
	//ajax method for status action from dasdboard
	public void changeOrderStatus(int orId,String status)
	{
		Orders o=service.findById(orId);
		if(status.equals("Received"))
		{
			o.setReceivedDate(LocalDate.now());
			o.setStatus(Status.Received);
			
		}
		else if(status.equals("Delivered"))
		{
			o.getDelivery().setDeliveryDate(LocalDate.now());
			o.setStatus(Status.Delivered);
			
		}
		service.changeStatus(o);
		orderList=service.findAll();
	}

	public OrderService getService() {
		return service;
	}

	public void setService(OrderService service) {
		this.service = service;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public List<Orders> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}
	
	

}
