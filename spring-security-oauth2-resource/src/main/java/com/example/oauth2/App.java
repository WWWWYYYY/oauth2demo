package com.example.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 教程视频地址：https://www.bilibili.com/video/av48590637/?p=17
 * https://www.bilibili.com/video/av74851468?p=67
 * 接入oauth2以后 资源将不可直接访问。需要获取到access_token 才能访问
 * 1、访问 http://localhost:9999/oauth/authorize?client_id=client&response_type=code 返回 code
 * 2、获取token
 *  * curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=authorization_code&code=1JuO6V' "http://client:secret@localhost:9999/oauth/token"
 * 3、访问资源服务时携带token
 */
@MapperScan(basePackages = "com.example.oauth2.mapper")
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}  
