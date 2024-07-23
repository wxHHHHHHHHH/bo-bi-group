package com.boss.bobi.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.common.utils.PasswordEncryptUtil;
import com.boss.bobi.login.entity.User;
import com.boss.bobi.login.model.FindUserParam;
import com.boss.bobi.login.service.UserService;
import com.boss.bobi.login.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wx
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-02 17:10:12
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public CommonResult<?> registerUser(User user) {
        user.setPassword(PasswordEncryptUtil.encrypt(user.getPassword()));
        this.save(user);
        return CommonResult.build(ResultCode.SUCCEED);
    }

    @Override
    public User findByNickName(String nickName) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getNickName, nickName));
    }

    @Override
    public CommonResult<?> findUser(FindUserParam findUserParam) {
        String nickName = findUserParam.getNickName();
        String position = findUserParam.getPosition();
        String startTime = findUserParam.getStartTime();
        String endTime = findUserParam.getEndTime();

        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.select(User::getId, User::getNickName, User::getGender, User::getCreateTime, User::getPosition);
        if (StringUtils.isNotBlank(nickName)) {
            qw.eq(User::getNickName, nickName);
        }
        if (StringUtils.isNotBlank(position)) {
            qw.eq(User::getPosition, position);
        }
        if (StringUtils.isNotBlank(startTime)) {
            qw.eq(User::getCreateTime, startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            qw.eq(User::getCreateTime, endTime);
        }
        qw.orderByAsc(User::getCreateTime);
        PageHelper.startPage(findUserParam.getPageNo(), findUserParam.getPageSize());
        PageInfo<User> pageInfo = new PageInfo<>(this.list(qw));
        return CommonResult.build(ResultCode.SUCCEED, pageInfo);
    }

}




