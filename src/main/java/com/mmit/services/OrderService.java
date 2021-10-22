package com.mmit.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Delivery;
import com.mmit.entity.Orders;
import com.mmit.entity.Orders.Status;



@Stateless
public class OrderService {
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private LoginBean loginBean;

	public List<Orders> findAll() {
		List<Orders> list=em.createNamedQuery("findAllOrders", Orders.class).getResultList();
		list.forEach(o->o.getDetails().forEach(od->{}));//for lazy initialize
		
		return list;
	}

	public void saveOrder(Orders order, String receiverName, String receiverAddress, String receiverPhone) {
		order.setCustomer(loginBean.getLoginUser());
		
		Delivery d=new Delivery();
		d.setReceiver(receiverName);
		d.setAddress(receiverAddress);
		d.setPhone(receiverPhone);
		
		order.addDelivery(d);
		order.setStatus(Status.Pending);
		em.persist(order);
		
	}

	public List<Orders> getOrderFindByLoginId(int loginId) {
		TypedQuery<Orders> query=em.createNamedQuery("orderListFingByLoginId", Orders.class);
		query.setParameter("loginId", loginId);
		List<Orders> list=query.getResultList();
		
		//for lazily initialize error
		list.forEach(o->o.getDetails().forEach(od->{}));
		return list;
	}

	public Orders findById(int oid) {
		Orders order=em.find(Orders.class, oid);
		order.getDetails().forEach(od->{});//for lazy initialize
		return order;
	}

	public void changeStatus(Orders o) {
		em.merge(o);
		
	}
	

}
