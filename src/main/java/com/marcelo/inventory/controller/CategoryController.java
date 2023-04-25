package com.marcelo.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.inventory.model.Category;
import com.marcelo.inventory.response.CategoryResponseRest;
import com.marcelo.inventory.services.ICategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

	@Autowired
	private ICategoryService service;
	
	/**
	 * get all categories
	 * @return
	 */
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> findAll(){
		return service.findAll();	
	}
	
	/**
	 * get category by id
	 * @param id
	 * @return
	 */
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	/**
	 * save category
	 * @param Category
	 * @return
	 */
	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
		return service.save(category);
	}
	
}
