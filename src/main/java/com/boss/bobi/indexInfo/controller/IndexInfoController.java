package com.boss.bobi.indexInfo.controller;

import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.indexInfo.entity.IndexInfo;
import com.boss.bobi.indexInfo.service.IndexInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : IndexInfoController  //类名
 * @Description :   //描述
 * @Date: 2024-09-09  17:26
 */
@RestController
@RequestMapping("/indexInfo")
public class IndexInfoController {


    @Autowired
    private IndexInfoService indexInfoService;


    @GetMapping("/findIndexVideo")
    public CommonResult<?> findIndexVideo() {
        return CommonResult.build(ResultCode.SUCCEED, indexInfoService.findIndexVideo());
    }

    @PostMapping("/saveIndexInfo")
    public CommonResult<?> saveIndexInfo(@RequestBody IndexInfo indexInfo) {
        indexInfoService.saveIndexInfo(indexInfo);
        return CommonResult.build(ResultCode.SUCCEED);
    }

}
