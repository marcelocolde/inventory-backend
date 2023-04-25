package com.marcelo.inventory.services;

import org.springframework.http.ResponseEntity;

import com.marcelo.inventory.model.Category;
import com.marcelo.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	
	public ResponseEntity<CategoryResponseRest> findAll();
	
	public ResponseEntity<CategoryResponseRest> findById(Long id);
		
	public ResponseEntity<CategoryResponseRest> save (Category category);
	
	public ResponseEntity<CategoryResponseRest> update (Category category, Long id);
	
	public ResponseEntity<CategoryResponseRest> deleteById(Long id);

	
}
