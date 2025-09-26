package com.mertalptekin.orderservicedemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.messaging.model.SubmitOrderMessage;
import com.mertalptekin.orderservicedemo.dto.SubmitOrder;
import com.mertalptekin.orderservicedemo.model.Order;
import com.mertalptekin.orderservicedemo.model.OutboxEvent;
import com.mertalptekin.orderservicedemo.producer.SubmitOrderProducer;
import com.mertalptekin.orderservicedemo.repository.OrderRepository;
import com.mertalptekin.orderservicedemo.repository.OutboxRepository;
import com.mertalptekin.orderservicedemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OrdersController {

    private final SubmitOrderProducer orderProducer;
    private final OrderService orderService;

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

    @PostMapping("submitOrderDebezium")
    public ResponseEntity sendOrder(@RequestBody SubmitOrder orderDto) {

       var response =  orderService.submitOrder(orderDto);

        return  ResponseEntity.ok().body(response);
    }


    @GetMapping("/orderedProducts")
    public  String getOrderedProducts() {
        return   ", Ordered Products";
    }


}
