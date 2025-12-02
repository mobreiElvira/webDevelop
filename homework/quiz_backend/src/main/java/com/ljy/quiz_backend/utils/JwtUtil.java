package com.ljy.quiz_backend.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // 生成密钥（实际项目中要保存在配置中）
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("mySuperSecretKey1234567890abcdef".getBytes());

    // Token 有效期：1小时（可根据需要调整）
    private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;

    // 生成 JWT 字符串
    public static String generateToken(String username) {
        long expirationMillis = 1000 * 60 * 60; // 1 小时
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析 JWT 并获取用户名
    public static String parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    // 生成 JWT 字符串
    public static String generateTokenWithClaims(Claims claim) {
        return Jwts.builder()
                .setClaims(claim)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }


    // 解析 JWT
    public static Claims parseTokenReturnClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
