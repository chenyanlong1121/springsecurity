package com.example.demo.Utils;


import com.example.demo.entity.DbPosition;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
//    public static final String APPSECRET_KEY = "JWT_secret";
//    public static final String TOKEN_HEADER="Authorization";
//    public static final String TOKEN_PREFIX="Cestbon";
//    public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
//    public static final String SUBJECT="qwer";
//    public static String CreateToken(DbPosition position){
//        String token= Jwts.builder()
//                .setSubject(SUBJECT)
//                .setIssuedAt(new Date())
//                .claim("username",position.getEmployeeId())
//                .claim("password",position.getDepartmentId())
//                .claim("authorities",position.getPosition())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
//                // 签名算法和密钥
//                .signWith(SignatureAlgorithm.HS512, APPSECRET_KEY)
//                .compact();
//        return token;
//    }
}
