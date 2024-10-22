package com.boss.bobi.indexInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boss.bobi.config.TokenValueConfig;
import com.boss.bobi.indexInfo.entity.IndexInfo;
import com.boss.bobi.indexInfo.service.IndexInfoService;
import com.boss.bobi.indexInfo.mapper.IndexInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wx
 * @description 针对表【index_info】的数据库操作Service实现
 * @createDate 2024-09-09 17:18:12
 */
@Service
public class IndexInfoServiceImpl extends ServiceImpl<IndexInfoMapper, IndexInfo>
        implements IndexInfoService {

    @Autowired
    private TokenValueConfig tokenValueConfig;

    @Override
    public List<IndexInfo> findIndexVideo() {
        return this.list(new LambdaQueryWrapper<IndexInfo>().orderByDesc(IndexInfo::getCreateTime).last("limit 5"));
    }

    @Override
    public void saveIndexInfo(IndexInfo indexInfo) {
        indexInfo.setCreateUser(tokenValueConfig.getNickName());
        indexInfo.setCreateTime(new Date());
        this.save(indexInfo);
    }

}




