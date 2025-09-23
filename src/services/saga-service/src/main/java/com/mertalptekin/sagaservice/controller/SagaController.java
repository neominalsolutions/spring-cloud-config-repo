package com.mertalptekin.sagaservice.controller;



import com.mertalptekin.sagaservice.event.OrderSubmittedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.random.RandomGenerator;

@RestController
@RequestMapping("/api/v1/saga")
public class SagaController {

    private StreamBridge streamBridge;

    public SagaController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/submit")
    public void submitOrder() {

        this.streamBridge.send("sendOrderSubmitEvent",new OrderSubmittedEvent(UUID.randomUUID().toString()));
    }

}
