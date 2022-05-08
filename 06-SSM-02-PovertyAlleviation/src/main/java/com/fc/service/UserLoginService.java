package com.fc.service;

import com.fc.entity.UserLoginInfo;

import java.util.Map;

public interface UserLoginService {
    Map<String, Object> login(UserLoginInfo userLoginInfo);
}
