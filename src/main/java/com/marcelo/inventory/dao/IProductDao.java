package com.marcelo.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.marcelo.inventory.model.Product;

public interface IProductDao extends CrudRepository<Product, Long>{

}
