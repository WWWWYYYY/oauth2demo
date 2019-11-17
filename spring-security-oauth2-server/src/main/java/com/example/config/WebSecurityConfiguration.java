package com.example.config;

import com.example.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 3.1、@EnableGlobalMethodSecurity(securedEnabled=true) 开启@Secured 注解过滤权限
 *
 * 3.2、@EnableGlobalMethodSecurity(jsr250Enabled=true)开启@RolesAllowed 注解过滤权限
 *
 * 3.3、@EnableGlobalMethodSecurity(prePostEnabled=true) 使用表达式时间方法级别的安全性         4个注解可用
 *
 * @PreAuthorize 在方法调用之前, 基于表达式的计算结果来限制对方法的访问
 *
 * @PostAuthorize 允许方法调用, 但是如果表达式计算结果为false, 将抛出一个安全性异常
 *
 * @PostFilter 允许方法调用, 但必须按照表达式来过滤方法的结果
 *
 * @PreFilter 允许方法调用, 但必须在进入方法之前过滤输入值
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义认证与授权
        auth.userDetailsService(userDetailsService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 将 check_token 暴露出去，否则资源服务器访问时报 403 错误
        web.ignoring().antMatchers("/oauth/check_token");
    }

    /**
     * 注入AuthenticationManager接口，启用OAuth2密码模式
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
}
