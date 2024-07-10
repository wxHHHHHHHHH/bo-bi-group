package com.boss.bobi.login.service;

import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.login.vo.LoginParam;

public interface LoginService {

    CommonResult<?> goLogin(LoginParam param);
}
