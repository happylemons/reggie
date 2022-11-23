package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.SetMealDish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealDishDao {

    void add(SetMealDish setMealDish);

    void deleteBySetMealIds(@Param("ids") List<Long> ids);

}
