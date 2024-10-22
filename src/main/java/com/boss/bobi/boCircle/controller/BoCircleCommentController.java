package com.boss.bobi.boCircle.controller;

import com.boss.bobi.boCircle.entity.BoCircleComment;
import com.boss.bobi.boCircle.service.BoCircleCommentService;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : BoCircleCommentController  //类名
 * @Description :   //描述
 * @Date: 2024-08-16  15:44
 */
@RestController
@RequestMapping("/BoCircleComment")
public class BoCircleCommentController {

    @Autowired
    private BoCircleCommentService boCircleCommentService;

    /**
     * 发表评论
     *
     * @param
     * @return
     */
    @PostMapping("/saveBoCircleComment")
    public CommonResult<?> saveBoCircleComment(@RequestBody BoCircleComment boCircleComment) {
        boCircleCommentService.saveBoCircleComment(boCircleComment);

        return CommonResult.build(ResultCode.SUCCEED, boCircleCommentService.findBoCircleCommentInfo(boCircleComment.getBoCircleId()));
    }

}
