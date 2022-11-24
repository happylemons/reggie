package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.User;

public interface UserDao {
    User findByPhone(String phone);

    void add(User user);
}
