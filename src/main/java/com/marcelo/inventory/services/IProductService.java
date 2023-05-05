package com.marcelo.inventory.services;

import org.springframework.http.ResponseEntity;

import com.marcelo.inventory.model.Product;
import com.marcelo.inventory.response.ProductResponseRest;

public interface IProductService {
	
	public ResponseEntity<ProductResponseRest> save(Product product,Long categoryId);
	
	public ResponseEntity<ProductResponseRest> findById(Long id);
	
	public ResponseEntity<ProductResponseRest> findByName(String name);
	
	public ResponseEntity<ProductResponseRest> deleteById(Long id);
	
	public ResponseEntity<ProductResponseRest> findAll();
	
}
