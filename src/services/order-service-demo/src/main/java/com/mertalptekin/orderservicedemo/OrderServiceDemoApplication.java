package com.mertalptekin.orderservicedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mertalptekin.orderservicedemo.client")
public class OrderServiceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceDemoApplication.class, args);
    }

}
