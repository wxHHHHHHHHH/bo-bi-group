package com.boss.bobi.activeInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boss.bobi.activeInfo.entity.ActiveInfo;
import com.boss.bobi.activeInfo.service.ActiveInfoService;
import com.boss.bobi.activeInfo.mapper.ActiveInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wx
* @description 针对表【active_info】的数据库操作Service实现
* @createDate 2024-10-14 16:44:04
*/
@Service
public class ActiveInfoServiceImpl extends ServiceImpl<ActiveInfoMapper, ActiveInfo>
    implements ActiveInfoService{

    @Override
    public List<ActiveInfo> findActiveInfo() {
        return this.list(new LambdaQueryWrapper<ActiveInfo>().orderByAsc(ActiveInfo::getId));
    }



}




