package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Orders;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired(required = false)
    private OrderService orderService;

    @PostMapping("submit")
    public R<String> submit(@RequestBody Orders order, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        order.setUserId(userId);
        orderService.submit(order, userId);
        return R.success("下单成功");
    }

    @GetMapping("userPage")
    public R<Page<Orders>> userPage(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "1") Integer pageSize,
                                    HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        return orderService.userPage(page, pageSize, userId);

    }


}
