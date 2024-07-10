package com.boss.bobi.login.controller;

import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.login.service.LoginService;
import com.boss.bobi.login.vo.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : LoginController  //类名
 * @Description : 登录 controller  //描述
 * @Date: 2024-07-02  17:11
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/goLogin")
    public CommonResult<?> goLogin(@RequestBody LoginParam param){
        return loginService.goLogin(param);
    }
}
