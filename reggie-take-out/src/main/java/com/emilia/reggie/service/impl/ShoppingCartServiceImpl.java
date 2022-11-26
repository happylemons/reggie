package com.emilia.reggie.service.impl;

import com.emilia.reggie.dao.ShoppingCartDao;
import com.emilia.reggie.model.entity.ShoppingCart;
import com.emilia.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired(required = false)
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        ShoppingCart dbShoppingCart = shoppingCartDao.findByUserIdWithDidOrSid(shoppingCart.getUserId(), shoppingCart.getDishId(), shoppingCart.getSetmealId());
        if (dbShoppingCart == null) {
            shoppingCart.setNumber(1);
            shoppingCartDao.insert(shoppingCart);
            dbShoppingCart = shoppingCart;
        } else {
            Integer number = dbShoppingCart.getNumber() + 1;
            Long id = dbShoppingCart.getId();
            shoppingCartDao.update(number, id);
        }
        return dbShoppingCart;

    }

    @Override
    public void sub(Long userId, Long dishId, Long setmealId) {
        ShoppingCart dbShoppingCart = shoppingCartDao.findByUserIdWithDidOrSid(userId, dishId, setmealId);
        if (dbShoppingCart.getNumber() == 1) {
            shoppingCartDao.delete(userId, dishId, setmealId);
        } else {
            Integer number = dbShoppingCart.getNumber() - 1;
            Long id = dbShoppingCart.getId();
            shoppingCartDao.update(number, id);
        }

    }

    @Override
    public List<ShoppingCart> findByUserId(Long userId) {

        return shoppingCartDao.findByUserId(userId);
    }

    @Override
    public void clean(Long userId) {
        shoppingCartDao.clean(userId);
    }


}
