package com.example.oauth2.security;

import com.example.oauth2.enmus.SecurityType;
import com.example.oauth2.service.SecurityConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 */
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private SecurityConfigService securityConfigService;

    private static final Map<String, Map<RequestMatcher, Collection<ConfigAttribute>>> REQUEST_MAP = new ConcurrentHashMap<>();


    public MyFilterInvocationSecurityMetadataSource(SecurityConfigService securityConfigService) {
        this.securityConfigService=securityConfigService;
        loadResourceDefine();
    }

    /**
     * 加载资源配置
     */
    public void loadResourceDefine() {
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        Map<RequestMatcher, Collection<ConfigAttribute>> map;
        Map<String, Map<RequestMatcher, Collection<ConfigAttribute>>> currentMap = new ConcurrentHashMap<>(4);
        List<com.example.oauth2.domain.SecurityConfig > securityConfigList = securityConfigService.findAll();
        for (com.example.oauth2.domain.SecurityConfig securityConfig : securityConfigList) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(securityConfig.getPattern());
            map = currentMap.get(securityConfig.getType());
            if (null == map) {
                map = new ConcurrentHashMap<>(4);
                currentMap.put(securityConfig.getType(), map);
            }
            array = map.get(matcher);
            if (null == array) {
                array = new ArrayList<>();
                map.put(matcher, array);
            }
            cfg = new SecurityConfig(securityConfig.getType());
            array.add(cfg);
        }
        REQUEST_MAP.clear();
        REQUEST_MAP.putAll(currentMap);
    }

    /**
     * 获取某个受保护的安全对象object的所需要的权限信息,是一组ConfigAttribute对象的集合，如果该安全对象object不被当前SecurityMetadataSource对象支持,则抛出异常IllegalArgumentException。
     * 该方法通常配合boolean supports(Class<?> clazz)一起使用，先使用boolean supports(Class<?> clazz)确保安全对象能被当前SecurityMetadataSource支持，然后再调用该方法。
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (SecurityType securityType : SecurityType.values()) {
            Map<RequestMatcher, Collection<ConfigAttribute>> entry = REQUEST_MAP.get(securityType.getType());
            if (null != entry) {
                for (RequestMatcher matcher : entry.keySet()) {
                    if (matcher.matches(request)) {
                        return entry.get(matcher);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取该SecurityMetadataSource对象中保存的针对所有安全对象的权限信息的集合。该方法的主要目的是被AbstractSecurityInterceptor用于启动时校验每个ConfigAttribute对象
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        for (SecurityType securityType : SecurityType.values()) {
            Map<RequestMatcher, Collection<ConfigAttribute>> entry = REQUEST_MAP.get(securityType.getType());
            if (null != entry) {
                entry.values().forEach(allAttributes::addAll);
            }
        }
        return allAttributes;
    }

    /**
     * 这里clazz表示安全对象的类型，该方法用于告知调用者当前SecurityMetadataSource是否支持此类安全对象，只有支持的时候，
     * 才能对这类安全对象调用getAttributes方法
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
