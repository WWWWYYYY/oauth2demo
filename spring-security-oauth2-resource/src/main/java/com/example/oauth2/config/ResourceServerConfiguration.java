package com.example.oauth2.config;

import com.example.oauth2.security.MyFilterInvocationSecurityMetadataSource;
import com.example.oauth2.security.SecurityAccessDecisionManager;
import com.example.oauth2.service.SecurityConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;


/**
 *  http://localhost:9999/oauth/authorize?client_id=client&response_type=code
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "oauth2-resource";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //resourceId 用于分配给可授予的clientId
        resources.resourceId(RESOURCE_ID).stateless(true);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/*").hasAuthority("SystemContentInsert")
//                .antMatchers("/insert/**").hasAuthority("SystemContentInsert")
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setAccessDecisionManager(securityAccessDecisionManager());
//                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource());
//                        return o;
//                    }
//                });
                // 以下为配置所需保护的资源路径及权限，需要与认证服务器配置的授权部分对应
                .antMatchers("/").permitAll()
                .antMatchers("/view/**").hasAuthority("SystemContentView")
                .antMatchers("/insert/**").hasAuthority("SystemContentInsert")
                .antMatchers("/update/**").hasAuthority("SystemContentUpdate")
                .antMatchers("/delete/**").hasAuthority("SystemContentDelete");

    }

    @Bean
    FilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource() {
        return new MyFilterInvocationSecurityMetadataSource(securityConfigService);
    }

    @Bean
    AccessDecisionManager securityAccessDecisionManager() {
        return new SecurityAccessDecisionManager();
    }

    @Autowired
    private SecurityConfigService securityConfigService;

}