package com.emilia.reggie.service;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Category;
import com.emilia.reggie.model.entity.Page;

import java.util.List;

public interface CategoryService {

    Integer add(Category category);

    R<Page> page(Integer page, Integer pageSize);

    void delete(Long id);

    void update(Category category);

    List<Category> findAll();

    R<List<Category>> list(Integer type);
}
