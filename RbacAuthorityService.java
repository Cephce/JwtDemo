package com.jwt.cephce.demo.system;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: cephce
 * @date: 2019/10/16 9:54
 * @description: 权限访问控制
 */
@Component("rbacauthorityservice")
public class RbacAuthorityService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission  = false;

        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();
            /**
            // 获取角色权限
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) userInfo).getAuthorities();
            Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
            //循环角色权限，判断是否具有当前路径的权限
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    //admin 可以访问的资源（一般数据库里维护，这里模拟数据库中查询）
                    Set<String> urls = new HashSet();
                    urls.add("/sys/**");
                    urls.add("/test/**");
                    AntPathMatcher antPathMatcher = new AntPathMatcher();
                    for (String url : urls) {
                        if (antPathMatcher.match(url, request.getRequestURI())) {
                            hasPermission = true;
                            break;
                        }
                    }
                }
            }
             */
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
            }

            return hasPermission;
        } else {
            return false;
        }
    }
}

