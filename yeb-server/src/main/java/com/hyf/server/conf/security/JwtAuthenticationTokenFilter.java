package com.hyf.server.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：洪yf
 * @date：2022/3/13
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
       String authHeader = request.getHeader(this.tokenHeader);
       //存在token
       if(null!=authHeader && authHeader.startsWith(this.tokenHead)){
           String authToken = authHeader.substring(this.tokenHead.length());

           String username = jwtTokenUtils.getUsernameFromToken(authToken);

            //token存在用户名但未登录
           if(null != username && null == SecurityContextHolder.getContext().getAuthentication()){
               //登录
               UserDetails userDetails = userDetailsService.loadUserByUsername(username);
               //验证token是否有效，重新设置用户对象
               if(jwtTokenUtils.valiDateToken(authToken,userDetails)){
                   UsernamePasswordAuthenticationToken authenticationToken =
                           new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                   authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               }

           }

       }
        filterChain.doFilter(request,response);
    }
}
