package com.mertalptekin.orderservicedemo.producer;

import com.mertalptekin.messaging.model.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final StreamBridge streamBridge;

    public void sendOrder(String message) {
        streamBridge.send("orderOut","message");
        System.out.println("Sent order: " + message);
    }
}
