package com.springboot.similar.system.service.impl;

import com.springboot.similar.annoation.springmvc.Service;
import com.springboot.similar.common.Result;
import com.springboot.similar.system.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    public Result<Object> toLogin(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            return Result.ok();
        }
        return Result.error(500, "用户名或密码错误");
    }
}

