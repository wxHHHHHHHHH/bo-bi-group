package com.boss.bobi.login.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName : LoginParam  //类名
 * @Description : 登录传参  //描述
 * @Date: 2024-07-02  17:36
 */
@Getter
@Setter
public class LoginParam {

    //昵称
    @NotBlank
    private String nickName;
    //密码
    @NotBlank
    private String password;

}
