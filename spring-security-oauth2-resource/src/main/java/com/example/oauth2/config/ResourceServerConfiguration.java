package com.example.oauth2.config;

import com.example.oauth2.security.MyFilterInvocationSecurityMetadataSource;
import com.example.oauth2.security.SecurityAccessDecisionManager;
import com.example.oauth2.service.SecurityConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 *  http://localhost:9999/oauth/authorize?client_id=client&response_type=code
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "oauth2-resource";


    @Autowired
    private DataSource dataSource;

    /**
     * 指定token的持久化策略
     * InMemoryTokenStore表示将token存储在内存
     * Redis表示将token存储在redis中
     * JdbcTokenStore存储在数据库中
     * @return
     */
    @Bean
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //resourceId 用于分配给可授予的clientId
        resources.resourceId(RESOURCE_ID).stateless(true);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
////                .antMatchers("/*").hasAuthority("SystemContentInsert")
////                .antMatchers("/insert/**").hasAuthority("SystemContentInsert")
////                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
////                    @Override
////                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
////                        o.setAccessDecisionManager(securityAccessDecisionManager());
////                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource());
////                        return o;
////                    }
////                });
//                // 以下为配置所需保护的资源路径及权限，需要与认证服务器配置的授权部分对应
//                .antMatchers("/").permitAll()
//                .antMatchers("/view/**").hasAuthority("SystemContentView")
//                .antMatchers("/insert/**").hasAuthority("SystemContentInsert")
//                .antMatchers("/update/**").hasAuthority("SystemContentUpdate")
//                .antMatchers("/delete/**").hasAuthority("SystemContentDelete");
        http.authorizeRequests()
                //指定不同请求方式访问资源所需要的权限，一般查询是read，其余是write。
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .and()
                .headers().addHeaderWriter((request, response) -> {
            response.addHeader("Access-Control-Allow-Origin", "*");//允许跨域
            if (request.getMethod().equals("OPTIONS")) {//如果是跨域的预检请求，则原封不动向下传达请求头信息
                response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });
    }

//    @Bean
//    FilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource() {
//        return new MyFilterInvocationSecurityMetadataSource(securityConfigService);
//    }
//
//    @Bean
//    AccessDecisionManager securityAccessDecisionManager() {
//        return new SecurityAccessDecisionManager();
//    }
//
//    @Autowired
//    private SecurityConfigService securityConfigService;

}