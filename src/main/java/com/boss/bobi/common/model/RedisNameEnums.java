package com.boss.bobi.common.model;

import lombok.Getter;

/**
 * @ClassName : RedisNameEnums  //类名
 * @Description :   //描述
 * @Date: 2024-07-03  17:52
 */
public class RedisNameEnums {

    @Getter
    public enum TokenKey{
        TOKEN("token_", "redis存储前缀");

        private String code;
        private String desc;

        TokenKey(String code,String desc){
            this.code = code;
            this.desc = desc;
        }

    }


}
