package com.boss.bobi.yunPan.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : YCreateFile  //类名
 * @Description : 获取创建文件返回结果  //描述
 * @Date: 2024-08-05  17:12
 */
@Setter
@Getter
public class YCreateFile {

    Integer fileID;
    String preuploadID;
    Boolean reuse;
    Long sliceSize;

}
