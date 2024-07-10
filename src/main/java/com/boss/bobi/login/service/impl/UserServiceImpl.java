package com.boss.bobi.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.common.utils.PasswordEncryptUtil;
import com.boss.bobi.login.entity.User;
import com.boss.bobi.login.service.UserService;
import com.boss.bobi.login.mapper.UserMapper;
import org.springframework.stereotype.Service;

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

}




