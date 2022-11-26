package com.emilia.reggie.service;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Orders;
import com.emilia.reggie.model.entity.Page;

public interface OrderService {
    void submit(Orders order,Long userId);

    R<Page<Orders>> userPage(Integer page, Integer pageSize, Long userId);
}
