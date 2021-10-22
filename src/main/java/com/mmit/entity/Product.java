package com.mmit.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column;
import javax.persistence.JoinTable;

@Entity
@NamedQuery(name = "findAllProduct",query = "SELECT p FROM Product p ORDER BY p.id  DESC")
@NamedQuery(name = "findByIdProduct",query = "SELECT p FROM Product p WHERE p.id=:id")
@NamedQuery(name = "filterByCatId",query = "SELECT p FROM Product p WHERE p.category.id=:catId")
@NamedQuery(name = "filterByBrandId",query = "SELECT p FROM Product p WHERE p.brand.id=:brandId")
@NamedQuery(name = "findProductName",query = "SELECT p FROM Product p WHERE p.brand.id =:bId AND p.category.id =:cId AND p.name =:pname")

public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = true)
	private String name;
	private int price;
	@Column(columnDefinition = "TEXT")
	private String productDetails;
	private String photo;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private Users created_by;
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private Users updated_by;
	
	@CreationTimestamp
	private LocalDate created_at;
	@UpdateTimestamp
	private LocalDate updated_at;
	
	private enum Status{Active,InActive}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Users getCreated_by() {
		return created_by;
	}
	public void setCreated_by(Users created_by) {
		this.created_by = created_by;
	}
	public Users getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(Users updated_by) {
		this.updated_by = updated_by;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public LocalDate getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}
	public LocalDate getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDate updated_at) {
		this.updated_at = updated_at;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
}
