package com.mertalptekin.orderservicedemo.controller;

import com.mertalptekin.messaging.model.Order;
import com.mertalptekin.orderservicedemo.producer.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderProducer orderProducer;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public  String get() {
        return   ", Port: " + port;
    }

    @PostMapping("/submitOrder")
    public String sendOrder() {
        orderProducer.sendOrder("order-message");
        return "Order sent!";
    }


}
