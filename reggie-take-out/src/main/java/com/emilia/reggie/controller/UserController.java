package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.User;
import com.emilia.reggie.model.vo.UserVo;
import com.emilia.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @PostMapping("sendMsg")
    public R sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        //String code = ValidateCodeUtils.generateValidateCode(4).toString();
        String code = "1234";//发送的验证码
        //SMSUtils.sendMessage("黑马旅游网", "SMS_205125234", phone, code);
        //session.setAttribute(phone, code); //学了redis之后, 将验证码存在redis中,并设置过期时间1min
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

        return R.success("发送成功");

    }

    @PostMapping("login")
    public R login(@RequestBody UserVo userVo, HttpSession session) {
        String phone = userVo.getPhone(); //手机号
        //String code = userVo.getCode(); //用户输入的验证码
        //String sendCode = (String) session.getAttribute(phone); //发送的验证码
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        String sendCode = valueOperations.get(phone).toString();
        String sendCode = valueOperations.get(phone).toString();
        R<User> result = userService.login(userVo, sendCode);
        if (result.getCode() == 1) {
            User user = result.getData();
            session.setAttribute("userId", user.getId());
            redisTemplate.delete(phone); //登陆成功之后,删除该手机号的验证码
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
