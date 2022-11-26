package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.UserDao;
import com.emilia.reggie.model.entity.User;
import com.emilia.reggie.model.vo.UserVo;
import com.emilia.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserDao userDao;

    @Override
    public R<User> login(UserVo userVo, String sendCode) {
        String phone = userVo.getPhone();
        User user = userDao.findByPhone(phone);

        if (user == null) {
            user = new User();
            user.setPhone(phone);
            userDao.add(user);
        }

        String code = userVo.getCode();
        if (sendCode.equals(code)) {
            return R.success(user);
        }
        return R.error("验证码错误,请重新登录");

    }
}



