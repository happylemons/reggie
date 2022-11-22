package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.Category;

import java.util.List;

public interface CategoryDao {

    Integer add(Category category);

    Category findByName(String name);

    List<Category> findAll();

    void delete(Long id);

    void update(Category category);

    List<Category> list(Integer type);

    Category findById(Long categoryId);
}
