package com.boss.bobi.login.mapper;

import com.boss.bobi.login.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wx
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-07-02 17:10:12
* @Entity com.boss.bobi.login.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




