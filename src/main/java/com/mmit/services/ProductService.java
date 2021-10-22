package com.mmit.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Brand;
import com.mmit.entity.Category;
import com.mmit.entity.Product;

@Stateless
public class ProductService {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private LoginBean loginBean;

	public List<Product> findAll() {
		TypedQuery<Product> query=em.createNamedQuery("findAllProduct", Product.class);
		return query.getResultList();
	}

	public void save(Product product) {
		if(product.getId() ==0)
		{
			product.setCreated_by(loginBean.getLoginUser());
			em.persist(product);
		}
		else
		{
			product.setUpdated_by(loginBean.getLoginUser());
			em.merge(product);
			
		}
		
	}

	public Product findById(int id) {
		//TypedQuery<Product> query=em.createNamedQuery("findByIdProduct", Product.class);
		//query.setParameter("id", id);
		return em.find(Product.class, id);
	}

	public void deleteProduct(int id) {
		Product pro=em.find(Product.class, id);
		em.remove(pro);
		
	}

	public List<Product> filterByCategoryId(int catId) {
		TypedQuery<Product> query=em.createNamedQuery("filterByCatId", Product.class);
		query.setParameter("catId", catId);
		return query.getResultList();
	}

	public List<Product> filterByBrand(int brandId) {
		TypedQuery<Product> query=em.createNamedQuery("filterByBrandId", Product.class);
		query.setParameter("brandId", brandId);
		return query.getResultList();
	}

	public String findCatNameByCatId(int catId) {
		TypedQuery<String> query=em.createNamedQuery("findCatNameByCatId", String.class);
		query.setParameter("catId", catId);
		return query.getSingleResult();
	}

	public String findBrandNameByBrandId(int bId) {
		TypedQuery<String> query=em.createNamedQuery("findBrandNameByBrandId", String.class);
		query.setParameter("bId", bId);
		return query.getSingleResult();
	}

	//read data form fileupload
	public void uploadData(Part uploadFile) {
		
		
		try {
			//read each line in notepad file
				BufferedReader br=new BufferedReader(new InputStreamReader(uploadFile.getInputStream()));
				String line=null;
				while((line=br.readLine()) != null)
				{
					createProduct(line);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//createProduct from fileupload
	private void createProduct(String line) {
		//line=name	category	brand	price	description
		
		String data[]=line.split("\t");//tap key in notepad
		
		if(data.length==5)
		{
			Category category=getCategory(data[1]);//insert new if no have category or return old if have
			Brand brand=getBrand(data[2]);
			Product product=getProduct(category,brand,data[0]);
			product.setPrice(Integer.parseInt(data[3]));
			product.setProductDetails(data[4]);
		}
		
	}

	private Product getProduct(Category category, Brand brand, String pname) {
		TypedQuery<Product> query=em.createNamedQuery("findProductName", Product.class);
		query.setParameter("bId", brand.getId());
		query.setParameter("cId", category.getId());
		query.setParameter("pname", pname);
		Product p=null;
		try {
			
			p=query.getSingleResult();
			
		} catch (Exception e) {
			p=new Product();
			p.setBrand(brand);
			p.setCategory(category);
			p.setName(pname);
			em.persist(p);
		}
		return p;
	}

	private Brand getBrand(String bname) {
		TypedQuery<Brand> query=em.createNamedQuery("findByBrandName", Brand.class);
		query.setParameter("bname", bname);
		Brand b=null;
		try {
			
			b=query.getSingleResult();	
		} catch (Exception e) {
			b=new Brand();
			b.setName(bname);
			em.persist(b);
		}
		return b;
	}

	private Category getCategory(String cname) {
		
		TypedQuery<Category> query=em.createNamedQuery("findByCategoryName", Category.class);
		query.setParameter("cname", cname);
		Category c=null;
		try {
			c=query.getSingleResult();//manage state
			
		} catch (Exception e) {
			c=new Category();
			c.setName(cname);
			em.persist(c);
		}
		
		return c;//manage state
	}

}
