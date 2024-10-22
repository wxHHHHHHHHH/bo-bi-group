package com.boss.bobi.indexInfo.service;

import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.indexInfo.entity.IndexInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wx
* @description 针对表【index_info】的数据库操作Service
* @createDate 2024-09-09 17:18:12
*/
public interface IndexInfoService extends IService<IndexInfo> {

    List<IndexInfo> findIndexVideo();

    void saveIndexInfo(IndexInfo indexInfo);
}
