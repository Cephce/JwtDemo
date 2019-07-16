package com.jwt.cephce.demo.service.impl;

import com.jwt.cephce.demo.dao.UserMapper;
import com.jwt.cephce.demo.entity.UserEntity;
import com.jwt.cephce.demo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper demoMapper;

    @Override
    public List<UserEntity> getUser() {
        return demoMapper.getListUser();
    }

    @Override
    public void register(String username, String password) {
        //因为只是简单注册，故只是对密码加密保存，其他就不添加进来了
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String EncryptedPassword = bCryptPasswordEncoder.encode(password);
        demoMapper.register(username,EncryptedPassword);
    }

}
