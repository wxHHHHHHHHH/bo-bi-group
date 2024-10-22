package com.boss.bobi.login.controller;

import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.login.entity.User;
import com.boss.bobi.login.model.FindUserParam;
import com.boss.bobi.login.service.UserService;
import com.boss.bobi.login.vo.UpdatePasswordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

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


    /**
     * 查询用户
     */
    @PostMapping("/findUser")
    public CommonResult<?> findUser(@RequestBody FindUserParam findUserParam) {
        return userService.findUser(findUserParam);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    public CommonResult<?> updatePassword(@Valid @RequestBody UpdatePasswordVo updatePasswordVo) {
        return userService.updatePassword(updatePasswordVo);
    }

    /**
     * 查询自己信息
     */
    @GetMapping("/getMyself")
    public CommonResult<?> getMyself() {
        User user = userService.getMyself();
        return CommonResult.build(ResultCode.SUCCEED, user);
    }


    /**
     * 修改信息
     *
     * @return
     */
    @PostMapping("/updateUser")
    public CommonResult<?> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
