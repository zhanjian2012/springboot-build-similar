package com.springboot.similar.system.controller;

import com.springboot.similar.annoation.ioc.Autowired;
import com.springboot.similar.annoation.springmvc.GetMapping;
import com.springboot.similar.annoation.springmvc.RestController;
import com.springboot.similar.common.Result;
import com.springboot.similar.system.service.UserService;

/**
 * 测试注解登录类
 *
 * @author zhanjian
 * @date 2020年10月12日11:10:32
 */
@RestController("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 测试GetMapping
     * @return String
     */
    @GetMapping("/toLogin")
    public Result toLogin(String userName, String password) {
        System.out.println("userName=" + userName + ", password=" + password);
        return userService.toLogin(userName, password);
    }
}
