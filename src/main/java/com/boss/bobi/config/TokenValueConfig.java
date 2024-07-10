package com.boss.bobi.config;

import cn.hutool.json.JSONObject;
import com.boss.bobi.common.utils.AccessTokenUtil;
import com.boss.bobi.common.utils.JwtUtil;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenValueConfig {


    @Autowired
    private RedissonClient redissonClient;

    private JSONObject valueMap(){
        final String token = AccessTokenUtil.getToken();
        return JwtUtil.getJSONObject(token);
    }

    /**
     * 获取当前登录用户名称
     * @return
     */
    public String getNickName(){
        return valueMap().getStr("nickName");
    }

    /**
     * 获取当前登录用户的经销商编号
     * @return
     */
    public Integer getId(){
        return valueMap().getInt("id");
    }

    /**
     * 获取当前登录用户角色
     * @return
     */
    public Integer getLevel(){
        return valueMap().getInt("level");
    }


}
