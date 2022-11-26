package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.SetMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealDao {


    Integer findById(Long id);

    void add(SetMeal setMeal);

    List<SetMeal> findByName(String name);

    Long findByIds(@Param("ids") List<Long> ids);

    void delete(@Param("ids") List<Long> ids);

    void updateStatusByIds(List<Long> ids, Integer status);

    List<SetMeal> findByCategoryId(Long categoryId, Integer status);
}
