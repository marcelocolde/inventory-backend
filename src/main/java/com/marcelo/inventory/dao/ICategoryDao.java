package com.marcelo.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.marcelo.inventory.model.Category;

public interface ICategoryDao extends CrudRepository<Category, Long> {

}
