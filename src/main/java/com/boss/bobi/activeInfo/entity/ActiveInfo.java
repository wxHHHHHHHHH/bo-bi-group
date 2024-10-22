package com.boss.bobi.activeInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName active_info
 */
@TableName(value ="active_info")
@Data
public class ActiveInfo implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 文件路径
     */
    @TableField(value = "src")
    private String src;

    /**
     * 插入时间
     */
    @TableField(value = "insert_time")
    private Date insertTime;

    /**
     * 封面
     */
    @TableField(value = "video_poster")
    private String videoPoster;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}