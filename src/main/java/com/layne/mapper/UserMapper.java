package com.layne.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.layne.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByUsernameAndPassword(String username,String password);
}
