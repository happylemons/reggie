package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishDao {


    List<Dish> findByCategoryId( Long id,  Integer status);

    void addDish(Dish dish);

    List<Dish> findByName(@Param("name") String name);

    Dish findByIdToPojo(Long id);

    void updateDish(Dish dish);

    Long findByIds(List<Long> ids);

    void delete(List<Long> ids);

    void updateStatusByIds(List<Long> ids, Integer status);
}
