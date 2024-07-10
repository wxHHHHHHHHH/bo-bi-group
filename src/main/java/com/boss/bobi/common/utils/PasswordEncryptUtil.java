package com.boss.bobi.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密类
 *
 * @author meng.zheng
 * @version 1.0.0
 * @module
 * @description
 * @createTime 2022年03月28日 15:10
 */
public class PasswordEncryptUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 加密
     *
     * @author meng.zheng
     */
    public static String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 匹配
     *
     * @author meng.zheng
     */
    public static boolean match(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

}
