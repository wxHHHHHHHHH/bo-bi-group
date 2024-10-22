package com.boss.bobi.activeInfo.controller;

import com.boss.bobi.activeInfo.entity.ActiveInfo;
import com.boss.bobi.activeInfo.service.ActiveInfoService;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : ActiveInfoController  //类名
 * @Description :   //描述
 * @Date: 2024-10-14  16:45
 */

@RestController
@RequestMapping("/activeInfo")
public class ActiveInfoController {

    @Autowired
    private ActiveInfoService activeInfoService;

    @GetMapping("/findActiveInfo")
    public CommonResult<?> findActiveInfo() {
        return CommonResult.build(ResultCode.SUCCEED, activeInfoService.findActiveInfo());
    }




}

