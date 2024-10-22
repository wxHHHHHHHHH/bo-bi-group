package com.boss.bobi.yunPan.controller;

import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.yunPan.service.YunPanService;
import com.boss.bobi.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName : YunPanController  //类名
 * @Description : 云盘controller  //描述
 * @Date: 2024-08-05  16:54
 */
@RestController
@RequestMapping("/upload")
public class YunPanController {


    @Autowired
    private YunPanService yunPanService;

    /**
     * 上传图片
     */
    @PostMapping("/img")
    public CommonResult<?> uploadImg(MultipartFile file) throws IOException {
        return CommonResult.build(ResultCode.SUCCEED, yunPanService.uploadImg(file));
    }

}
