package com.mertalptekin.orderservicedemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order-service")
public class OrdersController {



    @Value("${serviceName}")
    private  String serviceName;


    @GetMapping
    public  String get() {
        return  "ServiceName: " + serviceName;
    }


}
