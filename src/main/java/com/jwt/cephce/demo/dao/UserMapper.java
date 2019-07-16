package com.jwt.cephce.demo.dao;

import com.jwt.cephce.demo.entity.SelfUserDetails;
import com.jwt.cephce.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    //通过username查询用户
    SelfUserDetails getUser(@Param("username") String username);

    List<UserEntity> getListUser();

    void register(@Param("username") String username, @Param("password") String password);
}
