package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartDao {


    ShoppingCart findByUserIdWithDidOrSid(@Param("userId") Long userId, @Param("dishId") Long dishId, @Param("setmealId") Long setmealId);

    void insert(ShoppingCart shoppingCart);

    void update(Integer number,Long id);

    List<ShoppingCart> findByUserId(Long userId);

    void clean(Long userId);

    void delete(Long userId, Long dishId, Long setmealId);
}
