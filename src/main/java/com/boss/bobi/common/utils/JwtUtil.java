package com.boss.bobi.common.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.boss.bobi.login.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
public class JwtUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);



    public static String key = "fan_hua_si_jing_bu_guo_meng_yi_chang_ba_le_ba_le_yi_qie_dou_sui_feng_ba";

    /**
     * 创建token
     * @param user
     * @return
     */
    public static String createToken(User user) {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 72);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 内容
        payload.put("id", user.getId());
        payload.put("nickName",user.getNickName());
        payload.put("level", user.getLevel());
        String token = JWTUtil.createToken(payload, key.getBytes());
        LOG.info(token);
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static boolean validate(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        // validate包含了verify
        boolean validate = jwt.validate(0);
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        LOG.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

//    public static void main(String[] args) {
//        User user = new User();
//        user.setId(1);
//        user.setNickName("aca");
//        createToken(user);
//
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3MTk5MTY5MzYsIm5pY2tfbmFtZSI6ImFjYSIsImlkIjoxLCJleHAiOjE3MjAwMDMzMzYsImlhdCI6MTcxOTkxNjkzNn0.mUKBD47NydMAKFGFvOyVm47H1xeB8ReWlCOITFl4zSE";
//        System.out.println(validate(token));
//
//        getJSONObject(token);
//    }
}

