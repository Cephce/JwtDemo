package com.jwt.cephce.demo.service;

import com.jwt.cephce.demo.entity.Role;
import com.jwt.cephce.demo.util.ResultVO;

import java.util.Map;

public interface RoleService {

    Map<String,Object> deleteByPrimaryKey(Integer id);

    Map<String,Object> insert(Role record);

    Map<String,Object> insertSelective(Role record);

    Map<String,Object> selectByPrimaryKey(Integer id);

    Map<String,Object> updateByPrimaryKeySelective(Role record);

    Map<String,Object> updateByPrimaryKey(Role record);

    Map<String,Object> getListRoleByUserId(Integer userId);


}
