package com.marcelo.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.inventory.dao.ICategoryDao;
import com.marcelo.inventory.dao.IProductDao;
import com.marcelo.inventory.model.Category;
import com.marcelo.inventory.model.Product;
import com.marcelo.inventory.response.CategoryResponseRest;
import com.marcelo.inventory.response.ProductResponseRest;
import com.marcelo.inventory.utils.Util;

@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private IProductDao productDao;

	@Autowired
	private ICategoryDao categoryDao;
	
	// alternativa de inyeccion por constructor
//	private ICategoryDao categoryDao;
//	public ProductServiceImpl(ICategoryDao categoryDao) {
//		this.categoryDao = categoryDao;
//	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		
		try {
			Optional<Category> category =categoryDao.findById(categoryId);
			
			if(category.isPresent()) {
				product.setCategory(category.get());
			}else {
				response.setMetadata("Respuesta Error","-1","Categoria no encontrada");
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
			}
			Product productSaved = productDao.save(product);
			if(productSaved != null) {
				list.add(product);
				response.setMetadata("Respuesta Ok","00","Producto guardado");
				response.setProducts(list);				
			}else {
				response.setMetadata("Respuesta Error","-1","Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta Error","-1","Error al guardar el producto");
			return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response,HttpStatus.CREATED);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> findById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			Optional<Product> product = productDao.findById(id);
			
			if(product.isPresent()) {
				// descomprimir la foto
				product.get().setPicture(Util.decompressZLib(product.get().getPicture()));
				// agregar en el response
				list.add(product.get());
				response.setProducts(list);
				response.setMetadata("Respuesta ok","00","Se encontró el producto");
			}else {
				response.setMetadata("Respuesta Error","-1","No se encontró el producto con ID: "+id);
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta Error","-1","Error al buscar el producto ID: "+id);
			return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
	}

}
