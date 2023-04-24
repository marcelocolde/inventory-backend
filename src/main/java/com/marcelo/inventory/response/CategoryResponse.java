package com.marcelo.inventory.response;

import java.util.List;

import com.marcelo.inventory.model.Category;

import lombok.Data;

@Data
public class CategoryResponse {
		
		private List<Category> categoryList;
	
}
