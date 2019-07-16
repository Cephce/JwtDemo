package com.jwt.cephce.demo.service;

import com.jwt.cephce.demo.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> getUser();

    void register(String username, String password);
}
