package com.boss.bobi.login.service.impl;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.common.model.RedisNameEnums;
import com.boss.bobi.common.utils.JwtUtil;
import com.boss.bobi.common.utils.PasswordEncryptUtil;
import com.boss.bobi.login.entity.User;
import com.boss.bobi.login.service.LoginService;
import com.boss.bobi.login.service.UserService;
import com.boss.bobi.login.vo.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : LoginServiceImpl  //类名
 * @Description :   //描述
 * @Date: 2024-07-02  17:24
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public CommonResult<?> goLogin(LoginParam param) {
        //查询用户 并校验
        User user = userService.findByNickName(param.getNickName());
        if (null == user) {
            return CommonResult.build(ResultCode.DEFEATED, "用户不存在！");
        }
        if (!PasswordEncryptUtil.match(param.getPassword(), user.getPassword())) {
            return CommonResult.build(ResultCode.DEFEATED, "密码错误，请重新输入。");
        }
        //校验成功 生成token
        String token = JwtUtil.createToken(user);
        redisTemplate.opsForValue().set(RedisNameEnums.TokenKey.TOKEN.getCode() + user.getNickName(), token, 72, TimeUnit.HOURS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("nickName", user.getNickName());
        map.put("level", user.getLevel());
        map.put("dirId", user.getDirId());
        return CommonResult.build(ResultCode.SUCCEED,map);
    }
}
