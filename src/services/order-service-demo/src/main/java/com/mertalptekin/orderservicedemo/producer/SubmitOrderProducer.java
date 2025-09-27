package com.mertalptekin.orderservicedemo.producer;

import com.mertalptekin.messaging.model.SubmitOrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmitOrderProducer {

    private final StreamBridge streamBridge;

    public void sendOrder(SubmitOrderMessage order) {

        Message<SubmitOrderMessage> message = MessageBuilder.withPayload(order)
                .build();

        streamBridge.send("submitOrder-out-0",message);
        System.out.println("Sent order: " + message);
    }
}
