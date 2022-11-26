package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.ShoppingCart;
import com.emilia.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    @Autowired(required = false)
    private ShoppingCartService shoppingCartService;

    @PostMapping("add")
    public R add(@RequestBody ShoppingCart shoppingCart, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        shoppingCart.setUserId(userId);
        shoppingCart = shoppingCartService.add(shoppingCart);
        return R.success(shoppingCart);
    }

    @PostMapping("sub")
    public R sub(HttpSession session, Long dishId, Long setmealId) {
        Long userId = (Long) session.getAttribute("userId");
          shoppingCartService.sub(userId,dishId,setmealId);
        return R.success("删除成功");
    }


    @GetMapping("list")
    public R<List<ShoppingCart>> list(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        List<ShoppingCart> list = shoppingCartService.findByUserId(userId);
        return R.success(list);
    }

    @DeleteMapping("clean")
    public R clean(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        shoppingCartService.clean(userId);
        return R.success("清理购物车成功");
    }


}
