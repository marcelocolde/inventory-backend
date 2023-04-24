package com.marcelo.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.inventory.response.CategoryResponseRest;
import com.marcelo.inventory.services.ICategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

	@Autowired
	private ICategoryService service;
	
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> findAll(){
		
		return service.findAll();
	
	}
	
}
