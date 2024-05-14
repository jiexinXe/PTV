package com.example.ptv.utils;


import com.example.ptv.bean.ComUser;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class JwtUtils {


    //    private long expire;
//    private static final String secret = "admin";
    private String header;

    // 生成jwt
    public static String generateToken(ComUser user) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * 604800);
//        Map<String, Object> userMap = new HashMap<>();
//        userMap.put("user",user);
        System.out.println(user.getAuthorities());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(user.getUsername())//主题
                .setIssuedAt(nowDate) //jwt的签发时间
                .setExpiration(expireDate) // 7天過期
//                .setPayload(String.valueOf(user))//设置载荷  payload和claims不能同时指定
                .claim("user",user)
                .claim("authorities", user.getAuthorities())  // 添加权限信息到声明中
                .signWith(SignatureAlgorithm.HS512, "admin")//指定加密算法
                .compact();
    }

    // 解析jwt
    public static Claims getClaimByToken(String jwt) {
        try {
            return (Claims) Jwts.parser()
                    .setSigningKey("admin")
//                    .parseClaimsJwt(jwt)
                    .parse(jwt)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // jwt是否过期
    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}

