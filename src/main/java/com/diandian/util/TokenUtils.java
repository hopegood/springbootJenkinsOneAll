package com.diandian.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class TokenUtils {
	public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "crt";

    private static String secret;

    private static Long expiration;

    /**
     * 从 Token 中获取 usercode
     */
    public static String getUsercodeFromToken(String token) {
        String usercode;
        try {
            final Claims claims = getClaimsFromToken(token);
            usercode = claims.getSubject();
        } catch (Exception e) {
            usercode = e.toString();
        }
        return usercode;
    }


    /**
     * 从 Token 中获取创建时间
     */
    public static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = Objects.equals(null, claims) ? null : new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 从 Token 中获取过时时间
     */
    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成过期时间
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 验证 Token 是否过期
     */
    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 生成 Token
     */
    public static String generateToken(String usercode) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(CLAIM_KEY_USERNAME, usercode);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 生成 Token
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    /**
     * 刷新 Token
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证 Token
     */
   /* public static Boolean validateToken(String token,SystemUserModel user) {
        final String usercode = getUsercodeFromToken(token);
        return (usercode.equals(user.getUsercode()) && !isTokenExpired(token));
    }*/

    public static Boolean validateToken(String token,UserDetails userDetails) {
        final String usercode = getUsercodeFromToken(token);
        return (usercode.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        TokenUtils.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(Long expiration) {
        TokenUtils.expiration = expiration;
    }
}
