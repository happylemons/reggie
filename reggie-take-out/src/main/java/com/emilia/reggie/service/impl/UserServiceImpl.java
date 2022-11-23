package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.SetMealDao;
import com.emilia.reggie.dao.SetMealDishDao;
import com.emilia.reggie.dao.UserDao;
import com.emilia.reggie.exception.CustomerRelationException;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.entity.SetMeal;
import com.emilia.reggie.model.entity.SetMealDish;
import com.emilia.reggie.model.vo.SetMealVo;
import com.emilia.reggie.service.SetMealService;
import com.emilia.reggie.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserDao userDao;

}



