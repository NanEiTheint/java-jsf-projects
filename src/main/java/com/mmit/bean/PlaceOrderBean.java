package com.mmit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.OrderDetail;
import com.mmit.entity.Orders;
import com.mmit.entity.Users;
import com.mmit.services.OrderService;

@Named
@ViewScoped
public class PlaceOrderBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String receiverName;
	private String receiverAddress;
	private String receiverPhone;
	@Inject
	private LoginBean loginBean;
	@Inject
	private CartBean cartBean;
	@Inject
	private OrderService service;
	
	private List<Orders> orderList;
	
	private List<OrderDetail> orderDetailList;
	
	@PostConstruct
	private void init()
	{
		Users user=loginBean.getLoginUser();
		receiverName=user.getUserName();
		receiverAddress=user.getAddress();
		receiverPhone=user.getPhone();
		
		orderList=new ArrayList<Orders>();
		orderDetailList=new ArrayList<OrderDetail>();
	}
	//add order
	public String placeOrder()
	{
		service.saveOrder(cartBean.getOrder(),receiverName,receiverAddress,receiverPhone);
		cartBean.setOrder(new Orders());
		return "/index.xhtml?faces-redirect=true";
	}
	//find order id ajax method
	public void orderDetail(int Oid)
	{
		Orders order=service.findById(Oid);
		orderDetailList=order.getDetails();
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public List<Orders> getOrderList() {
		return service.getOrderFindByLoginId(loginBean.getLoginUser().getId());
	}
	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}
	
	
	

}
