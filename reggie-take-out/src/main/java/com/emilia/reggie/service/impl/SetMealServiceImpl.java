package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.SetMealDao;
import com.emilia.reggie.dao.SetMealDishDao;
import com.emilia.reggie.exception.CustomerRelationException;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.entity.SetMeal;
import com.emilia.reggie.model.entity.SetMealDish;
import com.emilia.reggie.model.vo.SetMealVo;
import com.emilia.reggie.service.SetMealService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired(required = false)
    private SetMealDao setMealDao;

    @Autowired(required = false)
    private SetMealDishDao setMealDishDao;


    @Override
    @CacheEvict(value = "setmeal", allEntries = true)//清除该名称空间下的所有数据
    //是否删除缓存中的所有条目。默认情况下，仅删除关联键下的值。请注意，不允许将此参数设置为true并指定key。
    public void add(SetMealVo setMealVo) {
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(setMealVo, setMeal);

        Long categoryId = Long.valueOf(setMealVo.getCategoryId());
        setMeal.setCategoryId(categoryId);

        setMeal.setCode("37643468236423");
        setMeal.setStatus(1);
        setMeal.setCreateTime(LocalDateTime.now());
        setMeal.setUpdateTime(LocalDateTime.now());
        setMeal.setIsDeleted(0);
        setMealDao.add(setMeal);

        List<SetMealDish> setMealDishes = setMealVo.getSetmealDishes();
        for (SetMealDish setMealDish : setMealDishes) {
            setMealDish.setSort(0);
            setMealDish.setSetMealId(setMeal.getId());
            setMealDish.setCreateTime(LocalDateTime.now());
            setMealDish.setUpdateTime(LocalDateTime.now());
            setMealDish.setCreateUser(setMealVo.getCreateUser());
            setMealDish.setUpdateUser(setMealVo.getUpdateUser());
            setMealDish.setIsDeleted(0);

            setMealDishDao.add(setMealDish);
        }


    }

    @Override
    public R<Page<SetMeal>> page(Integer page, Integer pageSize, String name) {

        PageHelper.startPage(page, pageSize);
        List<SetMeal> setMealList = setMealDao.findByName(name);
        PageInfo<SetMeal> pageInfo = new PageInfo<>(setMealList);
        Page<SetMeal> pages = new Page<>(pageInfo.getList(), pageInfo.getTotal(), pageSize, page);
        return R.success(pages);
    }

    @Override
    @CacheEvict(value = "setmeal" , allEntries = true)
    public void delete(List<Long> ids) {
        Long count = setMealDao.findByIds(ids);
        if (count > 0) {
            throw new CustomerRelationException("套餐在售, 请停售之后再删除!");
        }
        setMealDishDao.deleteBySetMealIds(ids);
        setMealDao.delete(ids);
    }

    @Override
    public void updateStatusByIds(List<Long> ids, Integer status) {
        setMealDao.updateStatusByIds(ids, status);
    }

    @Override
    @Cacheable(value = "setmeal", key = "#categoryId + '_' + #status")//多个参数作为key, 语法: #参数一 +'_' + 参数二
    public R<List<SetMeal>> list(Long categoryId, Integer status) {
        List<SetMeal> setMealList = setMealDao.findByCategoryId(categoryId, status);
        return R.success(setMealList);
    }
}



