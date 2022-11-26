package com.emilia.reggie.service;

import com.emilia.reggie.model.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart add(ShoppingCart shoppingCart);

    List<ShoppingCart> findByUserId(Long userId);

    void clean(Long userId);

    void sub(Long userId, Long dishId, Long setmealId);
}
