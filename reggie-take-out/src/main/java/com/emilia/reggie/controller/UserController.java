package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.User;
import com.emilia.reggie.model.vo.UserVo;
import com.emilia.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @PostMapping("sendMsg")
    public R sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        //String code = ValidateCodeUtils.generateValidateCode(4).toString();
        String code = "1234";
        //SMSUtils.sendMessage("黑马旅游网", "SMS_205125234", phone, code);
        session.setAttribute(phone, code);
        System.out.println(code);
        return R.success("发送成功");

    }

    @PostMapping("login")
    public R login(@RequestBody UserVo userVo, HttpSession session) {
        String phone = userVo.getPhone();//手机号
        String sendCode = (String) session.getAttribute(phone);//发送的验证码
        R<User> result = userService.login(userVo, sendCode);
        if (result.getCode() == 1) {
            User user = result.getData();
            session.setAttribute("userId", user.getId());
            return result;
        }
        return result;

    }

    @PostMapping("loginout")
    public R loginout(HttpSession session) {
        session.invalidate();
        return R.success("退出成功");
    }


}
