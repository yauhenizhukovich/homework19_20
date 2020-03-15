package com.gmail.supersonicleader.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class,
        scanBasePackages = {
                "com.gmail.supersonicleader.repository.impl",
                "com.gmail.supersonicleader.service.impl",
                "com.gmail.supersonicleader.web.controller",
                "com.gmail.supersonicleader.web.config"})
public class WebModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebModuleApplication.class, args);
    }

}
