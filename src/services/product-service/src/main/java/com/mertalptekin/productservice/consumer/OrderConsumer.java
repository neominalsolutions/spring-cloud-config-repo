package com.mertalptekin.productservice.consumer;

import com.mertalptekin.messaging.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrderConsumer {


    @Bean
    public Consumer<String> orderIn() {
        return message -> {
            System.out.println("Received order: " + message);
            // Burada iş mantığını yazabilirsin, örn DB kaydı vs.
        };
    }
}
