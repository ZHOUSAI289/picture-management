package com.gzlg.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String signKey;

    @Value("${jwt.expire}")
    private Long expire;

    /**
     * 生成JWT令牌
     * @return
     */
    public String generateJwt(Map<String,Object> claims){
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
    public Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
