package com.layne.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.layne.pojo.User;

public interface UserService extends IService<User> {
    User checkUser(String username,String password);
}
