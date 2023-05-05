package com.marcelo.inventory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.marcelo.inventory.model.Product;

public interface IProductDao extends CrudRepository<Product, Long>{
	
	@Query("select p from Product p where p.name like %?1% ") // consulta personalizada con JPQL
	public List<Product> findByNameLike(String name);
	
	public List<Product> findByNameContainingIgnoreCase(String name);
}
