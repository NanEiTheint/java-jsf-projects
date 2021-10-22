package com.mmit.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@NamedQuery(name = "findAllOrders",query = "SELECT o FROM Orders o ORDER BY o.orderDate DESC")
@NamedQuery(name = "orderListFingByLoginId",query = "SELECT o FROM Orders o WHERE o.customer.id=:loginId")
public class Orders implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Users customer;
	@CreationTimestamp
	private LocalDate orderDate;
	private LocalDate receivedDate;
	
	@OneToMany(mappedBy = "order", cascade = PERSIST)
	private List<OrderDetail> details=new ArrayList<OrderDetail>();
	
	@OneToOne(mappedBy = "order",cascade = PERSIST)
	private Delivery delivery=new Delivery();
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public enum Status{Pending,Received,Delivered}
	
	//orderDetail for hiper method
	public void addOrderItem(OrderDetail detail)
	{
		detail.setOrder(this);
		
		details.add(detail);
	}
	
	//delivery for hiper method
	public void addDelivery(Delivery delivery)
	{
		delivery.setOrder(this);
		this.setDelivery(delivery);
	}
	
	//delivery Status
//	@PrePersist
//	public void initializationStatus()
//	{
//		status=Status.Pending;
//	}
	
	public int getTotalQty()
	{
		return details.stream().mapToInt(q ->q.getSubQty()).sum();
		 
	}
	
	public double getTotalAmount()
	{
		return details.stream().mapToDouble(a ->a.getProduct().getPrice() * a.getSubQty()).sum();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getCustomer() {
		return customer;
	}

	public void setCustomer(Users customer) {
		this.customer = customer;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	
}
