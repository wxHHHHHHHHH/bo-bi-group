package com.boss.bobi.yunPan.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : YAccessToken  //类名
 * @Description : 获取token返回结果  //描述
 * @Date: 2024-08-05  17:10
 */
@Setter
@Getter
public class YAccessToken {
    //访问凭证
    String accessToken;
    // 必填	access_token过期时间
    String expiredAt;
}
