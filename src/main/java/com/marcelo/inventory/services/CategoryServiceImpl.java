package com.marcelo.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.inventory.dao.ICategoryDao;
import com.marcelo.inventory.model.Category;
import com.marcelo.inventory.response.CategoryResponseRest;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private ICategoryDao dao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> findAll() {
		
		CategoryResponseRest response = new CategoryResponseRest();
		
		try {
			List<Category> categories = (List<Category>) dao.findAll();
		
			response.setCategoryList(categories);
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta Error","-1","Error en la consulta hacia la BD");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> findById(Long id) {
		
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> categories = new ArrayList<>();
		
		try {
			Optional<Category> category = dao.findById(id);
			if(category.isPresent()) {
				categories.add(category.get());
				response.setCategoryList(categories);
				response.setMetadata("Respuesta ok","00","Categoria encontrada");
			}else {
				response.setMetadata("Respuesta Error","-1","Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta Error","-1","Error en la consulta por id hacia la BD");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.OK);
	}
	
}
