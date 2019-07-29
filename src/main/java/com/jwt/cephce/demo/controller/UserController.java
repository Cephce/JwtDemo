package com.jwt.cephce.demo.controller;

import com.jwt.cephce.demo.entity.UserEntity;
import com.jwt.cephce.demo.service.UserService;
import com.jwt.cephce.demo.util.ResultEnum;
import com.jwt.cephce.demo.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    private UserService orderService;

    @RequestMapping("/getUser")
    @ResponseBody
    public List<UserEntity> getUser(){
        List<UserEntity> result = orderService.getUser();
        return result;
    }

    @RequestMapping("/getUser2")
    @ResponseBody
    public List<UserEntity> getUser2(){
        List<UserEntity> result = orderService.getUser();
        return result;
    }

    /**
     * 简单注册功能
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Map<String, Object> register(String username, String password){
        orderService.register(username,password);
        return ResultVO.result(ResultEnum.SUCCESS,true);
    }

}
