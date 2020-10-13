package com.springboot.similar.system.controller;

import com.springboot.similar.annoation.springmvc.GetMapping;
import com.springboot.similar.annoation.springmvc.RestController;

/**
 * 测试注解登录类
 *
 * @author zhanjian
 * @date 2020年10月12日11:10:32
 */
@RestController("/login")
public class LoginController {

    /**
     * 测试GetMapping
     * @return String
     */
    @GetMapping("/toLogin")
    public String toLogin(String userName, String password) {
        return "userName=" + userName + ", password=" + password;
    }
}
