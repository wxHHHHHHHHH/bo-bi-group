package com.boss.bobi.login.controller;

import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.login.entity.User;
import com.boss.bobi.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : UserController  //类名
 * @Description : 用户controller  //描述
 * @Date: 2024-07-02  17:46
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 注册用户
     *
     * @return
     */
    @PostMapping("/registerUser")
    public CommonResult<?> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}