package com.marcelo.inventory.services;

import org.springframework.http.ResponseEntity;

import com.marcelo.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	
	public ResponseEntity<CategoryResponseRest> findAll();
	
}
