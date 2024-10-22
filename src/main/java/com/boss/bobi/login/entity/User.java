package com.boss.bobi.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 性别（0女，1男）
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 等级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 创建用户id
     */
    @TableField(value = "create_user")
    private Integer createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 职务
     */
    @TableField(value = "position")
    private String position;

    /**
     * 目录id
     */
    @TableField(value = "dir_id")
    private Integer dirId;

    /**
     * 头像地址
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 地区
     */
    @TableField(value = "area")
    private String area;

    /**
     * 姓名
     */
    @TableField(value = "real_name")
    private String realName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}