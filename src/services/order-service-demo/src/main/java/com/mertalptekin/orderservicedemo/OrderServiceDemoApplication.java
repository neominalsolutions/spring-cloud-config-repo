package com.mertalptekin.orderservicedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mertalptekin.orderservicedemo.client")
public class OrderServiceDemoApplication {


//    @Bean
//    public Consumer<String> submitOrder() {
//        return message -> {
//            System.out.println("Received order: " + message);
//            // Burada iş mantığını yazabilirsin, örn DB kaydı vs.
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceDemoApplication.class, args);
    }

}
