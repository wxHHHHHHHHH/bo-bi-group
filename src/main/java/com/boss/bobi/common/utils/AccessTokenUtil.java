package com.boss.bobi.common.utils;

public final class AccessTokenUtil {
    // access_token线程变量，保存access_token的值
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static String getToken(){
        return contextHolder.get();
    }
    public static void setToken(String token) {
        contextHolder.set(token);
    }

    public static void removeToken() {
        contextHolder.remove();
    }
}
