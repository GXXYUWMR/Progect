package com.fc.controller;

import com.fc.entity.User;
import com.fc.service.UserService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



//用户
@RestController
@RequestMapping("user")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("getlist")
    public ResultVO getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                            User user ) {

        return userService.getList(pageNum, pageSize,user);
    }

    @PostMapping("add")
    public ResultVO add(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("delete")
    public ResultVO delete(Long id) {
        return userService.delete(id);
    }


}