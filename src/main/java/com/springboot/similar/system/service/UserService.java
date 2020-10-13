package com.springboot.similar.system.service;

import com.springboot.similar.common.Result;

public interface UserService {

    Result<Object> toLogin(String userName, String password);
}
