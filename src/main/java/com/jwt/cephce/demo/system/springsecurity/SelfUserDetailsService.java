package com.jwt.cephce.demo.system.springsecurity;


import com.jwt.cephce.demo.dao.UserMapper;
import com.jwt.cephce.demo.entity.Role;
import com.jwt.cephce.demo.entity.SelfUserDetails;
import com.jwt.cephce.demo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * @author: cephce
 * @date: 2019/10/15 16:54
 * @description: 用户认证、权限
 */
@Component
@Slf4j
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过username查询用户
        SelfUserDetails user = userMapper.getUser(username);
        Map<String,Object> dataMap = roleService.getListRoleByUserId(user.getId());
        List<Role> roles = (List<Role>)dataMap.get("data");
        if(user == null){
            //仍需要细化处理
            throw new UsernameNotFoundException("该用户不存在");
        }

        Set authoritiesSet = new HashSet();
        // 获取角色权限
        Collection<? extends GrantedAuthority> authorities = ((UserDetails) user).getAuthorities();
        //查询用户权限
        for (Role role:roles) {
            // 模拟从数据库中获取用户角色
            GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_"+role.getRoleName().toUpperCase());
            authoritiesSet.add(auth);
        }

        user.setAuthorities(authoritiesSet);

        log.info("用户{}验证通过",username);
        return user;
    }
}

