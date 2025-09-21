package com.mertalptekin.orderservicedemo.consumer;


import com.mertalptekin.messaging.model.OrderStockConfirmed;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrderStockConfirmedConsumer {

    @Bean
    public Consumer<OrderStockConfirmed> orderStockConfirmed() {
        return event -> {
            System.out.println("Order Stock Confirmed" + event.id() + event.message());
        };
    }


}
