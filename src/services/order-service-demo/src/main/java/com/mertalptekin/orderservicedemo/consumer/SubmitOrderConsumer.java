package com.mertalptekin.orderservicedemo.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SubmitOrderConsumer {

    @Bean
    public Consumer<String> submitOrder() {
        return message -> {
            System.out.println("Received order 0: " + message);
            // Burada iş mantığını yazabilirsin, örn DB kaydı vs.
        };
    }
}
