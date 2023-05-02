package com.marcelo.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.marcelo.inventory.dao.ICategoryDao;
import com.marcelo.inventory.dao.IProductDao;
import com.marcelo.inventory.model.Category;
import com.marcelo.inventory.model.Product;
import com.marcelo.inventory.response.CategoryResponseRest;
import com.marcelo.inventory.response.ProductResponseRest;

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

}
