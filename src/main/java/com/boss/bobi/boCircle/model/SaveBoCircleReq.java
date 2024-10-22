package com.boss.bobi.boCircle.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName : SaveBoCircleReq  //类名
 * @Description :   //描述
 * @Date: 2024-08-15  18:31
 */
@Getter
@Setter
public class SaveBoCircleReq {


    private String content;
    private List<String> fileUrls;
}
