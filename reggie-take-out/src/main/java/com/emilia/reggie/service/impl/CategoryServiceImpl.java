package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.CategoryDao;
import com.emilia.reggie.dao.DishDao;
import com.emilia.reggie.dao.SetmealDao;
import com.emilia.reggie.exception.NameExistsException;
import com.emilia.reggie.exception.CustomerRelationException;
import com.emilia.reggie.model.entity.Category;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired(required = false)
    private CategoryDao categoryDao;

    @Autowired(required = false)
    private DishDao dishDao;

    @Autowired(required = false)
    private SetmealDao setmealDao;

    @Override
    public Integer add(Category category) {

        Category findCategory = categoryDao.findByName(category.getName());
        if (findCategory != null) {
            throw new NameExistsException("菜品类型已经存在,请勿重复添加!");
        }
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());


        return categoryDao.add(category);
    }

    @Override
    public R<Page> page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Category> categoryList = categoryDao.findAll();
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        Page<Category> pages = new Page<>(pageInfo.getList(), pageInfo.getTotal(), pageSize, page);
        return R.success(pages);
    }

    @Override
    public void delete(Long id) {
        Integer dishCount = dishDao.findById(id);
        if (dishCount > 0) {
            throw new CustomerRelationException("该类别已经被菜品关联,删除失败!");
        }

        Integer setmealCount = setmealDao.findById(id);
        if (setmealCount > 0) {
            throw new CustomerRelationException("该套餐已经被菜品关联,删除失败!");
        }

        categoryDao.delete(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryDao.update(category);

    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
