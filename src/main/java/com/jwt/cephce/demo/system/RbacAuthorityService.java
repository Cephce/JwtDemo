package com.jwt.cephce.demo.system;

import com.jwt.cephce.demo.dao.RoleDao;
import com.jwt.cephce.demo.dao.RoleMenuDao;
import com.jwt.cephce.demo.dao.UserMapper;
import com.jwt.cephce.demo.entity.RoleMenu;
import com.jwt.cephce.demo.util.redis.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author: cephce
 * @date: 2019/10/16 9:54
 * @description: 权限访问控制
 */
@Component("rbacauthorityservice")
@Slf4j
public class RbacAuthorityService {

    @Autowired
    private UserMapper demoMapper;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission  = false;

        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();

            // 获取角色权限
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) userInfo).getAuthorities();
            Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
            //循环角色权限，判断是否具有当前路径的权限
            Set<String> urls = new HashSet();
            for (GrantedAuthority authority : authorities) {
                //查询当前角色所能访问的菜单
                //判断当前用户是否有权限
                if (!StringUtil.isEmpty(authority.getAuthority())) {
                    //admin 可以访问的资源（一般数据库里维护，这里模拟数据库中查询）
                    //urls.add("/JwtDemo/**");
                    String auth = authority.getAuthority();
                    String[] roleName = auth.split("_");
                    List<RoleMenu> roleMenus = roleMenuDao.getListRoleMenuByRoleName(roleName[1].toLowerCase());
                    for(RoleMenu roleMenu:roleMenus){
                        urls.add(roleMenu.getMenuUrl());
                    }
                }
            }
            log.info(request.getRequestURI());
            //判断当前用户是否有访问路径的权限
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
/*
            //获取资源
            Set<String> urls = new HashSet();
            // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！
            urls.add("/JwtDemo/**");//application.yml里设置了项目路径，百度一下我就不贴了
            Set set2 = new HashSet();
            Set set3 = new HashSet();
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }*/

            return hasPermission;
        } else {
            return false;
        }
    }
}

