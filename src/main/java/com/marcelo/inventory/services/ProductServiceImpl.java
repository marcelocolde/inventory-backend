package com.marcelo.inventory.services;

import java.util.ArrayList;
import java.util.Arrays;
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

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> findByName(String name) {
		
		ProductResponseRest response = new ProductResponseRest();
		
		try {
			List<Product> products = productDao.findByNameContainingIgnoreCase(name);
			
			if(products.size() > 0) {
				// descomprimir las fotos
				products.stream().forEach( p -> {
					p.setPicture(Util.decompressZLib(p.getPicture()));
				});
				// agregar en el response
				response.setProducts(products);
				response.setMetadata("Respuesta ok","00","Se encontraron resultados por ese nombre");
			}else {
				response.setMetadata("Respuesta Error","-1","No se encontraron productos con nombre: "+name);
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta Error","-1","Error al buscar productos por nombre");
			return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
		
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> deleteById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		
		try {
			Optional<Product> product = productDao.findById(id);
			
			if(product.isPresent()) {
				productDao.deleteById(id);
				response.setMetadata("Respuesta ok","00","Se eliminó correctamente el producto ID: "+id);
				response.setProducts(Arrays.asList(product.get()));
			}else {
				response.setMetadata("Respuesta Error","-1","No se encontró el producto con ID: "+id);
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta Error","-1","Error al eliminar producto con ID: "+ id);
			return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> findAll() {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<Product>();
		
		try {
			List<Product> listAux = (List<Product>) productDao.findAll();
			if(listAux.size() > 0) {
				listAux.stream().forEach(p -> {
					p.setPicture(Util.decompressZLib(p.getPicture()));
					list.add(p);
				});
				response.setMetadata("Respuesta ok","00","Exito al obtener todos los productos ");
				response.setProducts(list);
			}else {
				response.setMetadata("Respuesta error","-2","No se encuentran productos disponibles");
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta Error","-1","Error al obtener todos los productos ");
			return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
	}

}
