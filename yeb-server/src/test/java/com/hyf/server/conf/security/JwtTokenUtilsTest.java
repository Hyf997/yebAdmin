package com.hyf.server.conf.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author：洪yf
 * @date：2022/3/14
 */
@SpringBootTest
class JwtTokenUtilsTest {

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Test
    public void testGetUsernameFromToken() {
        Claims claimsFromRoken = jwtTokenUtils.getClaimsFromRoken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZXMiOjE2NDcyMzEzOTk4NDMsImV4cCI6MTY0NzgzNjE5OX0.S96Flf0_BP2d1tJQIJg-stAwaZmHuC89NqjgWPuKN8RJqw0dxylm2lSLonjd9h-J696tlWWY8Rm5RaXqCi1Gpg");
        System.out.println(claimsFromRoken);
        String usernameFromToken = jwtTokenUtils.getUsernameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZXMiOjE2NDcyMzEzOTk4NDMsImV4cCI6MTY0NzgzNjE5OX0.S96Flf0_BP2d1tJQIJg-stAwaZmHuC89NqjgWPuKN8RJqw0dxylm2lSLonjd9h-J696tlWWY8Rm5RaXqCi1Gpg");
        System.out.println(usernameFromToken);

    }

    @Test
    void getClaimsFromRoken() {
    }

}