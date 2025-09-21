package com.mertalptekin.orderservicedemo.controller;

import com.mertalptekin.messaging.model.SubmitOrderMessage;
import com.mertalptekin.orderservicedemo.producer.SubmitOrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OrdersController {

    private final SubmitOrderProducer orderProducer;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public  String get() {
        return   ", Port: " + port;
    }

    @PostMapping("/submitOrder")
    public String sendOrder(@RequestBody SubmitOrderMessage message) {
        orderProducer.sendOrder(message);
        return "Order sent!";
    }


}
