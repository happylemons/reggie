package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.CategoryDao;
import com.emilia.reggie.dao.DishDao;
import com.emilia.reggie.dao.DishFlavorDao;
import com.emilia.reggie.model.entity.Category;
import com.emilia.reggie.model.entity.Dish;
import com.emilia.reggie.model.entity.DishFlavor;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.vo.DishFlavorVo;
import com.emilia.reggie.model.vo.DishVo;
import com.emilia.reggie.service.DishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DishServiceImpl implements DishService {

    @Autowired(required = false)
    private DishDao dishDao;

    @Autowired(required = false)
    private DishFlavorDao dishFlavorDao;

    @Autowired(required = false)
    private CategoryDao categoryDao;


    @Override
    public void addDish(DishVo dishVo) {

        Dish dish = new Dish(
                null,
                dishVo.getName(),
                dishVo.getCategoryId(),
                dishVo.getPrice(),
                dishVo.getCode(),
                dishVo.getImage(),
                dishVo.getDescription(),
                1,
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                dishVo.getCreateUser(),
                dishVo.getUpdateUser());

        dishDao.addDish(dish);


        ArrayList<DishFlavor> dishFlavors = new ArrayList<>();
        List<DishFlavorVo> flavorVos = dishVo.getFlavors();
        for (DishFlavorVo flavor : flavorVos) {
            DishFlavor dishFlavor = new DishFlavor();
            dishFlavor.setDishId(dish.getId());
            dishFlavor.setName(flavor.getName());
            dishFlavor.setValue(flavor.getValue());
            dishFlavor.setCreateTime(LocalDateTime.now());
            dishFlavor.setUpdateTime(LocalDateTime.now());
            dishFlavor.setCreateUser(dish.getCreateUser());
            dishFlavor.setUpdateUser(dish.getUpdateUser());
            dishFlavor.setIsDeleted(0);
            dishFlavors.add(dishFlavor);
        }

        dishFlavorDao.addDishFlavor(dishFlavors);
    }

    @Override
    public R<Page<DishVo>> findByPage(Integer page, Integer pageSize, String name) {
        //1.设置页面大小
        PageHelper.startPage(page, pageSize);

        //2.根据名字查询菜品的列表
        List<Dish> dishList = dishDao.findByName(name);

        //3.
        PageInfo<Dish> pageInfo = new PageInfo<>(dishList);
        List<DishVo> dishVoList = dishList.stream().map(dish -> {
            DishVo dishVo = new DishVo();
            //属性拷贝，把dish的属性值拷贝dishvo里面
            BeanUtils.copyProperties(dish, dishVo);
            Category category = categoryDao.findById(dish.getCategoryId());
            dishVo.setCategoryName(category.getName());
            return dishVo;
        }).collect(Collectors.toList());
        Page<DishVo> pages = new Page<>(dishVoList, pageInfo.getTotal(), pageSize, page);
        return R.success(pages);
    }

    @Override
    public R<DishVo> findById(Long id) {

        Dish dish = dishDao.findByIdToPojo(id);
        DishVo dishVo = new DishVo();
        BeanUtils.copyProperties(dish, dishVo);

        List<DishFlavor> dishFlavorList = dishFlavorDao.findByDishId(id);
        List<DishFlavorVo> dishFlavorVoList = dishVo.getFlavors();
        BeanUtils.copyProperties(dishFlavorList, dishFlavorVoList);

        return R.success(dishVo);
    }

    @Override
    public void update(DishVo dishVo) {
        Dish dish = new Dish();
        dish.setName(dishVo.getName());
        dish.setCategoryId(dishVo.getCategoryId());
        dish.setCode(dishVo.getCode());
        dish.setImage(dishVo.getImage());
        dish.setDescription(dishVo.getDescription());
        dish.setUpdateTime(LocalDateTime.now());
        dishDao.updateDish(dish);

        dishFlavorDao.deleteByDishId(dish.getId());

        ArrayList<DishFlavor> dishFlavors = new ArrayList<>();
        List<DishFlavorVo> flavorVos = dishVo.getFlavors();
        for (DishFlavorVo flavor : flavorVos) {
            DishFlavor dishFlavor = new DishFlavor();
            dishFlavor.setDishId(dish.getId());
            dishFlavor.setName(flavor.getName());
            dishFlavor.setValue(flavor.getValue());
            dishFlavor.setUpdateTime(LocalDateTime.now());
            dishFlavor.setUpdateUser(dish.getUpdateUser());
            dishFlavor.setIsDeleted(0);
            dishFlavors.add(dishFlavor);
        }
        dishFlavorDao.addDishFlavor(dishFlavors);
    }
}



