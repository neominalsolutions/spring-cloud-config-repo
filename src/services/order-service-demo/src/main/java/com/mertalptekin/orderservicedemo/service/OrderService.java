package com.mertalptekin.orderservicedemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.orderservicedemo.dto.SubmitOrder;
import com.mertalptekin.orderservicedemo.model.Order;
import com.mertalptekin.orderservicedemo.model.OutboxEvent;
import com.mertalptekin.orderservicedemo.repository.OrderRepository;
import com.mertalptekin.orderservicedemo.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public OutboxEvent submitOrder(SubmitOrder orderDto) {

        Order order = new Order();
        order.setProduct(orderDto.product());
        order.setPrice(orderDto.price());
        order.setQuantity(orderDto.quantity());
        order.setStatus(orderDto.status());



        orderRepository.save(order);



        OutboxEvent event = new OutboxEvent();
        event.setAggregateType("Order");
        event.setAggregateId(order.getId().toString());
        event.setType("OrderCreated");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = "";

        try {
            jsonPayload = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        event.setPayload(jsonPayload);
        outboxRepository.save(event);

        return event;
    }

}
