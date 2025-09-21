package com.mertalptekin.productservice.consumer;

import com.mertalptekin.messaging.model.OrderStockConfirmed;
import com.mertalptekin.messaging.model.OrderStockRejected;
import com.mertalptekin.messaging.model.SubmitOrderMessage;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class SubmitOrderConsumer {

    private  final StreamBridge streamBridge;



    @Bean
    public Consumer<SubmitOrderMessage> submitOrder() {
        return message -> {
            System.out.println("Product Service Received order: " + message);

            if(message.quantity() >10) {
                OrderStockRejected event = new OrderStockRejected(message.id(),"Stok yetersiz");
               boolean result = streamBridge.send("orderStockRejected-out-0",event);
                System.out.println("Sent Order Stock Rejected Event: " + event);
            } else {
                OrderStockConfirmed event = new OrderStockConfirmed(message.id(),message.product() + " ürünü Reserve edildi");

                streamBridge.send("orderStockConfirmed-out-0",event);
                System.out.println("Sent Order Stock Confirmed Event: " + event);
            }


            // Burada iş mantığını yazabilirsin, örn DB kaydı vs.
        };
    }
}
