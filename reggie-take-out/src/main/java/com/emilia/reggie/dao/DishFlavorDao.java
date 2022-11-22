package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.DishFlavor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishFlavorDao {


    void addDishFlavor(@Param("flavors") List<DishFlavor> flavors);

    List<DishFlavor> findByDishId(Long id);

    void deleteByDishId(Long dishId);
}
