package com.mertalptekin.gatewayservice.config;

import com.netflix.discovery.EurekaClient;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EurekaDeregistration {

    @Autowired
    private EurekaClient eurekaClient;

    @PreDestroy
    public void deregister() {
        System.out.println("Deregistering from Eureka...");
        eurekaClient.shutdown(); // Servisi Eureka'dan çıkarır
    }
}