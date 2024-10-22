package com.boss.bobi.boCircle.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.boss.bobi.login.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName : BoCircleVo  //类名
 * @Description :   //描述
 * @Date: 2024-08-15  18:48
 */
@Getter
@Setter
public class BoCircleVo {


    private Integer id;

    /**
     * 用户id
     */

    private Integer userId;

    /**
     * 文本内容
     */

    private String text;

    /**
     * 文件url集合
     */


    private List<String> fileUrls;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 删除标记
     */

    private Integer deleteFlag;

    /**
     * 预览url集合
     */

    private List<String> previewFileUrls;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 用户昵称
     */
    private String nickName;


    private List<LikeUserVo> userVos;

    private List<CommentUserVo> commentUserVos;


}
