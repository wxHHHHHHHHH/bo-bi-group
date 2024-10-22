package com.boss.bobi.boCircle.controller;

import com.boss.bobi.boCircle.entity.BoCircle;
import com.boss.bobi.boCircle.service.BoCircleLikeService;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : BoCircleLikeController  //类名
 * @Description :   //描述
 * @Date: 2024-08-16  15:36
 */
@RestController
@RequestMapping("/boCircleLike")
public class BoCircleLikeController {

    @Autowired
    private BoCircleLikeService boCircleLikeService;

    /**
     * 保存点赞
     * @param id
     * @return
     */
    @GetMapping("/saveBoCircleLike")
    public CommonResult<?> saveBoCircleLike(@RequestParam("id") Integer id) {
        boCircleLikeService.saveBoCircleLike(id);
        return CommonResult.build(ResultCode.SUCCEED);
    }
}
