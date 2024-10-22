package com.boss.bobi.login.service;

import com.boss.bobi.common.model.CommonResult;
import com.boss.bobi.login.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.boss.bobi.login.model.FindUserParam;
import com.boss.bobi.login.vo.UpdatePasswordVo;

/**
* @author wx
* @description 针对表【user】的数据库操作Service
* @createDate 2024-07-02 17:10:12
*/
public interface UserService extends IService<User> {

    CommonResult<?> registerUser(User user);

    User findByNickName(String nickName);

    CommonResult<?> findUser(FindUserParam findUserParam);

    CommonResult<?> updatePassword(UpdatePasswordVo updatePasswordVo);

    User getMyself();

    CommonResult<?> updateUser(User user);
}
