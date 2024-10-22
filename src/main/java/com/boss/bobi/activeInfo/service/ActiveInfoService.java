package com.boss.bobi.activeInfo.service;

import com.boss.bobi.activeInfo.entity.ActiveInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wx
* @description 针对表【active_info】的数据库操作Service
* @createDate 2024-10-14 16:44:05
*/
public interface ActiveInfoService extends IService<ActiveInfo> {

    List<ActiveInfo> findActiveInfo();


}
