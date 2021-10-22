package com.mmit.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmit.entity.Brand;

@Stateless
public class BrandService {
	@PersistenceContext
	private EntityManager em;

	public void save(Brand brand) {
		if(brand.getId()==0)
			em.persist(brand);
		else
			em.merge(brand);
		
	}

	public List<Brand> findAll() {
		TypedQuery<Brand> query=em.createNamedQuery("findAllBrand", Brand.class);
		return query.getResultList();
	}

	public Brand findById(int id) {
		return em.find(Brand.class, id);
	}
}
