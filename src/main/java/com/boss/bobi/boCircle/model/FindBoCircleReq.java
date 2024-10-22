package com.boss.bobi.boCircle.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : FindBoCircleReq  //类名
 * @Description :   //描述
 * @Date: 2024-08-15  18:42
 */

@Setter
@Getter
public class FindBoCircleReq {

    private Integer pageNo = 1;
    private Integer pageSize = 5;
    private Integer userId;



}
