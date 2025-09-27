package com.mertalptekin.productservice.consumer;

import com.mertalptekin.messaging.model.OrderStockConfirmed;
import com.mertalptekin.messaging.model.OrderStockRejected;
import com.mertalptekin.messaging.model.SubmitOrderMessage;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class SubmitOrderConsumer {

    private  final StreamBridge streamBridge;
    private final Tracer tracer;



    @Bean
    public Consumer<Message<SubmitOrderMessage>> submitOrder() {
        return message -> {

            SubmitOrderMessage payload = message.getPayload();


            if(payload.quantity() >10) {
                OrderStockRejected event = new OrderStockRejected(payload.id(),"Stok yetersiz");
               boolean result = streamBridge.send("orderStockRejected-out-0",event);
                System.out.println("Sent Order Stock Rejected Event: " + event);
            } else {
                OrderStockConfirmed event = new OrderStockConfirmed(payload.id(),payload.product() + " ürünü Reserve edildi");

                streamBridge.send("orderStockConfirmed-out-0",event);
                System.out.println("Sent Order Stock Confirmed Event: " + event);
            }


            // Burada iş mantığını yazabilirsin, örn DB kaydı vs.
        };
    }
}
