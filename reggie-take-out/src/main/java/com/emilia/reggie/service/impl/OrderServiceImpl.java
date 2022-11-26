package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.*;
import com.emilia.reggie.model.entity.*;
import com.emilia.reggie.service.OrderService;
import com.emilia.reggie.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    private OrderDao orderDao;
    @Autowired(required = false)
    private OrderDetailDao orderDetailDao;

    @Autowired(required = false)
    private ShoppingCartDao shoppingCartDao;

    @Autowired(required = false)
    private UserDao userDao;

    @Autowired(required = false)
    private AddressBookDao addressBookDao;


    @Override
    public void submit(Orders orders, Long userId) {
        //1.获取当前用户购物车数据
        List<ShoppingCart> shoppingCartList = shoppingCartDao.findByUserId(userId);

        //2.根据userId查询用户数据
        User user = userDao.findUserById(userId);

        //3.根据地址id,查询地址数据
        AddressBook addressBook = addressBookDao.findById(orders.getAddressBookId());

        Long orderId = UUIDUtils.getUUIDInOrderId().longValue();

        BigDecimal total = new BigDecimal("0");//只有字符串才能不精度丢失

        //4.遍历所有的购物项,组装order_detail订单,批量保存明细
        ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartList) {
            OrderDetail detail = new OrderDetail();
            detail.setId(UUIDUtils.getUUIDInOrderId().longValue());
            detail.setName(shoppingCart.getName());
            detail.setImage(shoppingCart.getImage());
            detail.setOrderId(orderId);
            detail.setDishId(shoppingCart.getDishId());
            detail.setSetmealId(shoppingCart.getSetmealId());
            detail.setDishFlavor(shoppingCart.getDishFlavor());
            detail.setNumber(shoppingCart.getNumber());
            detail.setAmount(shoppingCart.getAmount());

            BigDecimal amount = shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber() + ""));
            total = total.add(amount);

            orderDetailList.add(detail);
        }

        //5.保存订单信息Orders
        orders.setId(orderId);
        orders.setNumber(String.valueOf(orderId)); //订单号
        orders.setStatus(1); //状态
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setAmount(total);
        orders.setUserName(user.getName());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());

        //插入订单
        orderDao.save(orders);
        //批量插入订单项
        orderDetailDao.saveBatch(orderDetailList);


        //6. 删除当前用户的购物车列表数据
        shoppingCartDao.clean(userId);

    }

    @Override
    public R<Page<Orders>> userPage(Integer page, Integer pageSize, Long userId) {
        PageHelper.startPage(page, pageSize);
        List<Orders> userOrders = orderDao.findByUserId(userId);
        PageInfo<Orders> pageInfo = new PageInfo<Orders>(userOrders);
        Page<Orders> pages = new Page<>(pageInfo.getList(), pageInfo.getTotal(), pageSize, page);
        return R.success(pages);

    }
}
