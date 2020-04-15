package com.example.oauth2.security;

import com.example.oauth2.enmus.SecurityType;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;


public class SecurityAccessDecisionManager implements AccessDecisionManager {


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            String needCode = configAttribute.getAttribute();
            if (SecurityType.PERMIT_ALL.getType().equalsIgnoreCase(needCode)) {
                return;
            }
//            HttpServletRequest request = ((FilterInvocation) object).getRequest();
//            // 有传token
//            if (null != request.getHeader("Authorization")) {
//                return;
//            }
//            // 有传外部编码
//            if (BooleanUtils.isTrue(OuterKeyValidator.validOuterKey(request))) {
//                return;
//            }
        }
        throw new AccessDeniedException("无权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
