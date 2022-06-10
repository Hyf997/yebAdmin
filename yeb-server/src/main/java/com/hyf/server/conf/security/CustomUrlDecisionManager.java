package com.hyf.server.conf.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author：洪yf
 * @date：2022/3/21
 * * 权限控制
 * * 判断用户角色
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {


    @Override
    public void decide(final Authentication authentication, final Object object, final Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for(ConfigAttribute configAttribute:configAttributes){
            String needRole = configAttribute.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                //判断是否登录
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw  new AccessDeniedException("尚未登录，请登录！");
                } else {
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities =
                    authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw  new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(final ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return true;
    }
}
