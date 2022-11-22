package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishDao {


    Integer findById(Long id);

    void addDish(Dish dish);

    List<Dish> findByName(@Param("name") String name);

    Dish findByIdToPojo(Long id);

    void updateDish(Dish dish);
}
