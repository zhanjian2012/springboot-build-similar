package com.springboot.similar.system.compnent;

import com.springboot.similar.annoation.ioc.Compnent;
import com.springboot.similar.annoation.springmvc.GetMapping;

@Compnent
public class LoginController {

    @GetMapping
    public String login(String userName, String password) {
        return "userName=" + userName + ", password=" + password;
    }
}
