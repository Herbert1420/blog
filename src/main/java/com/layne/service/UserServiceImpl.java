package com.layne.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.layne.mapper.UserMapper;
import com.layne.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名,密码查询
     * @param username
     * @param password
     * @return
     */
    @Override
    public User checkUser(String username, String password) {
        return userMapper.findByUsernameAndPassword(username,password);
    }
}
