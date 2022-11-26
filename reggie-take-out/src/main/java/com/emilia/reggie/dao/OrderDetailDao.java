package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.OrderDetail;

import java.util.ArrayList;

public interface OrderDetailDao {
    void saveBatch(ArrayList<OrderDetail> orderDetailList);
}
