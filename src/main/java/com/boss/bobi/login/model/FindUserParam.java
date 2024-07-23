package com.boss.bobi.login.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : FindUserParam  //类名
 * @Description : 查询用户请求参数  //描述
 * @Date: 2024-07-19  10:13
 */
@Getter
@Setter
public class FindUserParam {

    private Integer pageNo = 1;
    private Integer pageSize = 6;

    //昵称
    private String nickName;
    //职位
    private String position;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
}
