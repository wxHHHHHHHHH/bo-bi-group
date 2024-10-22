package com.boss.bobi.boCircle.controller;

import com.boss.bobi.boCircle.model.FindBoCircleReq;
import com.boss.bobi.boCircle.model.SaveBoCircleReq;
import com.boss.bobi.boCircle.service.BoCircleService;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**

 *
 * @ClassName : BoCircleController  //类名
 * @Description :   波比圈
 * @Date: 2024-08-15  17:53
 */
@RestController
@RequestMapping("/boCircle")
public class BoCircleController {

    @Autowired
    private BoCircleService boCircleService;


    /**
     * 查询波比圈
     *
     * @param req
     * @return
     */
    @PostMapping("/findBoCircle")
    public CommonResult<?> findBoCircle(@RequestBody FindBoCircleReq req) {
        return CommonResult.build(ResultCode.SUCCEED, boCircleService.findBoCircle(req));
    }


    /**
     * 保存波比圈
     *
     * @param req
     * @return
     */
    @PostMapping("/saveBoCircle")
    public CommonResult<?> saveBoCircle(@RequestBody SaveBoCircleReq req) {
        boCircleService.saveBoCircle(req);
        return CommonResult.build(ResultCode.SUCCEED);
    }


}
