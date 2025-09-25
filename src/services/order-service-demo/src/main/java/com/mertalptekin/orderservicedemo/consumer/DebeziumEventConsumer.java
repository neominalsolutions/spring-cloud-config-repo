package com.mertalptekin.orderservicedemo.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

// Not Consumer class ismi ile method ismi aynı olmasın bean çaıkışması yaşanıyor
//

@Component
public class DebeziumEventConsumer {

    @Bean
    public Consumer<String> debeziumEvent() {
        return event -> {
            System.out.println("Debezium Order Submitted Event :" + event);
        };
    }


}
