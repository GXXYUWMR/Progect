package com.fc.service.impl;

import com.fc.dao.UserMapper;
import com.fc.entity.User;
import com.fc.entity.UserExample;
import com.fc.entity.UserLoginInfo;
import com.fc.service.UserLoginService;
import com.fc.util.JwtUtil;
import com.fc.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserMapper mapper;

    @Override
    public Map<String, Object> login(UserLoginInfo userLoginInfo) {

        String username = userLoginInfo.getUsername();
        String password = userLoginInfo.getPassword();

        Map<String, Object> map = new HashMap<>();

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = mapper.selectByExample(example);
        if (users == null) {
            map.put("code", -400);
            map.put("message", "没有这个用户");
            map.put("success", false);
            map.put("data", null);
            return map;
        }
        User user = users.get(0);
        UserLoginVo loginVo = new UserLoginVo(user.getId(), user.getUsername(), new Date());

        if (user.getPassword().equals(password)) {
            Map<String, Object> claim = new HashMap<>();
            claim.put("username", user.getUsername());
            claim.put("id", user.getId());
            claim.put("autoLogin", userLoginInfo.isAutoLogin());
            claim.put("isRemPassword", userLoginInfo.isRemPassword());
            claim.put("isRemUsername", userLoginInfo.isIsRemUsername());
            String token = JwtUtil.createToken(claim, System.currentTimeMillis() + 1000 * 30);
            map.put("message", "登录成功...");
            map.put("code", 200);
            map.put("success", true);
            map.put("data", loginVo);
            map.put("token", token);
        }
        return map;
    }
}
