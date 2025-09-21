package com.mertalptekin.orderservicedemo.consumer;

import com.mertalptekin.messaging.model.OrderStockRejected;
import com.mertalptekin.messaging.model.SubmitOrderMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrderStockRejectedConsumer {

    @Bean
    public Consumer<OrderStockRejected> orderStockRejected(){
        return message-> {
            System.out.println("Order Stock Rejected" + message.reason());
        };
    }

}






