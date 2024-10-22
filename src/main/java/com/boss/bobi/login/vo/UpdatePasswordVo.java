package com.boss.bobi.login.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName : UpdatePasswordVo  //类名
 * @Description :   //描述
 * @Date: 2024-08-29  15:39
 */
@Setter
@Getter
public class UpdatePasswordVo {

    @NotEmpty(message = "新密码不能为空！")
    private String newPassword;
}
