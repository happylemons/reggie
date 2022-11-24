package com.emilia.reggie.service;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.User;
import com.emilia.reggie.model.vo.UserVo;

public interface UserService {
    R<User> login(UserVo userVo, String sendCode);
}
