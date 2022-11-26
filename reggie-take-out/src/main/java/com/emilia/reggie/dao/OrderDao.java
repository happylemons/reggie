package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.Orders;

import java.util.List;

public interface OrderDao {

    void save(Orders orders);

    List<Orders> findByUserId(Long userId);
}
