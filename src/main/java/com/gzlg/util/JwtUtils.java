package com.gzlg.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static String signKey = "gzlg-picture-management-secret-key-2026-very-long-safe-key"; //密钥
    private static Long expire = 604800000L; //7天

    /**
     * 生成JWT令牌
     * @return
     */
    public static String generateJwt(Map<String,Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims) //添加自定义属性
                .signWith(SignatureAlgorithm.HS256, signKey) //设置签名算法
                .setExpiration(new Date(System.currentTimeMillis() + expire)) //设置令牌有效期
                .compact(); //生成令牌
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
