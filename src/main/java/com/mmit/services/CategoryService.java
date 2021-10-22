package com.mmit.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmit.entity.Category;

@Stateless
public class CategoryService {
		@PersistenceContext
		private EntityManager em;

		public void save(Category category) {
			if(category.getId()==0)
			{
				em.persist(category);
			}
			else
				
				em.merge(category);
			
		}

		public List<Category> findAll() {
			// TODO Auto-generated method stub
			List<Category> list=em.createNamedQuery("findAll", Category.class).getResultList();
			list.forEach(c->c.getProductList().forEach(p->{}));//lazy initial error
			return list;
		}

		public Category findById(int id) {
			
			return em.find(Category.class, id);
		}
	

}
