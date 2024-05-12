package com.example.ptv.service.Imp;


import com.example.ptv.bean.ComUser;
import com.example.ptv.entity.User;
import com.example.ptv.service.userService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    private userService usersService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        /**
         * 1/通过userName 获取到userInfo信息
         * 2/通过User（UserDetails）返回details。
         */
        //通过userName获取用户信息
        User user = usersService.getByName(userName);
        if(user == null) {
            throw new UsernameNotFoundException("not found");
        }
        // 定义权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 根据角色值映射角色名称
        String roleName = null;
        if (user.getRole()==1) {
            roleName = "ADMIN";
        } else if (user.getRole()==2) {
            roleName = "SUPERADMIN";
        } else if (user.getRole()==3) {
            roleName = "CUSTOMER";
        }

        // 如果角色名称不为空，则添加角色
        if (roleName != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
        }

        ComUser userDetails = new ComUser(user.getId(), user.getUsername(),user.getPassword(),authorities);
        System.out.println(userDetails.getAuthorities());
        return userDetails;
    }
}
