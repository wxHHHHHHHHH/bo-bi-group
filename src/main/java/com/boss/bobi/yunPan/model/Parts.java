package com.boss.bobi.yunPan.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : Parts  //类名
 * @Description :   //描述
 * @Date: 2024-08-05  17:42
 */
@Setter
@Getter
public class Parts {

    // 分片编号
    Integer partNumber;
    // 分片大小
    Integer size;
    // 分片md5
    Integer etag;

}
