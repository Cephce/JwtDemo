package com.jwt.cephce.demo.service.impl;

import com.jwt.cephce.demo.dao.RoleDao;
import com.jwt.cephce.demo.entity.Role;
import com.jwt.cephce.demo.service.RoleService;
import com.jwt.cephce.demo.util.ResultEnum;
import com.jwt.cephce.demo.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;


    @Override
    public Map<String,Object> deleteByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public Map<String,Object> insert(Role record) {
        return null;
    }

    @Override
    public Map<String,Object> insertSelective(Role record) {
        return null;
    }

    @Override
    public Map<String,Object> selectByPrimaryKey(Integer id) {
        Role role = roleDao.selectByPrimaryKey(id);
        return ResultVO.result(ResultEnum.SUCCESS, role,true);
    }

    @Override
    public Map<String,Object> updateByPrimaryKeySelective(Role record) {
        return null;
    }

    @Override
    public Map<String,Object> updateByPrimaryKey(Role record) {
        return null;
    }

    @Override
    public Map<String, Object> getListRoleByUserId(Integer userId) {
        List<Role> roleList = roleDao.getListRoleByUserId(userId);
        return ResultVO.result(ResultEnum.SUCCESS, roleList,true);
    }
}
