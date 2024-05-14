package com.example.ptv.sec.filter;




import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson.JSON;
import com.example.ptv.bean.ComUser;
import com.example.ptv.utils.JwtUtils;

import com.example.ptv.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;
    @Resource
    RedisUtil redisUtils;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //String jwt = request.getHeader(jwtUtils.getHeader());
        String jwt = request.getHeader("Authorization");
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            System.out.println(jwt);
            throw new JwtException("token 异常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token已过期");
        }
        String tem = JSON.toJSONString(claim.get("user"));
        ComUser user = JSON.parseObject(tem, ComUser.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Map<String, String>> authoritiesList = claim.get("authorities", List.class); // 获取权限部分的列表
        if (authoritiesList != null) {
            for (Map<String, String> authorityMap : authoritiesList) {
                String authorityName = authorityMap.get("authority"); // 获取权限对象中的 authority 字段
                authorities.add(new SimpleGrantedAuthority(authorityName)); // 创建 SimpleGrantedAuthority 对象并加入列表
            }
        }




        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                redisUtils.get("login:" + user.getUserId()), null, authorities);

        SecurityContextHolder.getContext().setAuthentication( authenticationToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current Authentication: " + authentication);
        System.out.println("Authorities: " + authentication.getAuthorities());

        chain.doFilter(request, response);
    }

}
