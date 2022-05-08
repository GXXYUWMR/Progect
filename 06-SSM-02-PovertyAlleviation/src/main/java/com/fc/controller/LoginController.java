package com.fc.controller;

import com.fc.entity.UserLoginInfo;
import com.fc.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class LoginController {
    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping("login")
    public Map<String,Object> login( UserLoginInfo userLoginInfo) {
        return userLoginService.login(userLoginInfo);
    }
    @RequestMapping("verifyToken")
    public Map<String, Object> verify(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("code", -1);
        map.put("message", "该干啥干啥");
        map.put("data", request.getAttribute("claim"));
        return map;
    }
}
