package com.hyf.server.conf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：洪yf
 * @date：2022/3/5
 *
 * jwtToken工具类
 */
@Component
public class JwtTokenUtils{
    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED = "creates";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    /**
     * create time: 2022/3/5
     * description:根据用户信息生成token
     * params:
     * return:
     */
     public String generateToken(UserDetails userDetails)
     {
         Map<String,Object> claims = new HashMap<>();
         claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
         claims.put(CLAIM_KEY_CREATED,new Date());
         return generateToken(claims);
     }

     /**
      * create time: 2022/3/5
      * description:从token中获取用户名
      * params:
      * return:
      */
     public String getUsernameFromToken(String token){
         String username;
         try {
             Claims claims = getClaimsFromRoken(token);
             username = claims.getSubject();
         }catch (Exception e){
             username = null;
         }
         return username;

     }

     /**
      * create time: 2022/3/5
      * description:从token中获取荷载
      * params:token
      * return:Claims
      */
     public Claims getClaimsFromRoken(String token){
         Claims claims = null;
         try {
             claims = Jwts.parser()
                     .setSigningKey(secret)
                     .parseClaimsJws(token)
                     .getBody();
         }catch (Exception e){
             e.printStackTrace();
         }
         return claims;
     }

     /**
      * create time: 2022/3/5
      * description:根据荷载生成JWT Token
      * params:
      * return:
      */
     private String generateToken(Map<String,Object> claims){
         return Jwts.builder()
                 .setClaims(claims)
                 .setExpiration(generateExpirationDate())
                 .signWith(SignatureAlgorithm.HS512,secret)
                 .compact();
     }

     /**
      * create time: 2022/3/5
      * description:生成token失效时间
      * params:
      * return:
      */
     private Date generateExpirationDate(){
         return new Date(System.currentTimeMillis()+expiration * 1000);
     }

     /**
      * create time: 2022/3/5
      * description:判断token是否失效
      * params:
      * return:
      */
     private boolean isTokenExpired(String token){
         Date expired = getExpiredDateFromToken(token);
         return expired.before(new Date());
     }

     /**
      * create time: 2022/3/5
      * description:token是否可以被刷新
      * params:
      * return:
      */
     public boolean canRefresh(String token){
         return !isTokenExpired(token);
     }

        /**
         * create time: 2022/3/5
         * description:刷新token
         * params:token
         * return:String
         */
     public String refleshToken(String token){
         Claims claims = getClaimsFromRoken(token);
         claims.put(CLAIM_KEY_CREATED,new Date());
         return generateToken(claims);
     }
     /**
      * create time: 2022/3/5
      * description:从token中获取过期时间
      * params:token
      * return:Date 过期时间
      */
    private Date getExpiredDateFromToken(String token) {
        Claims claimsFromRoken = getClaimsFromRoken(token);
        return claimsFromRoken.getExpiration();
    }

    /**
      * create time: 2022/3/5
      * description：验证token是否有效
      * params:
      * return:
      */
     public boolean valiDateToken(String token,UserDetails userDetails){
         String username = getUsernameFromToken(token);
         return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
     }

}
