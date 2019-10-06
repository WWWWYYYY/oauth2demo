package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


@EnableTransactionManagement
@MapperScan(basePackages = "com.example.mapper")
@SpringBootApplication
public class OAuth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServerApplication.class, args);
    }

}