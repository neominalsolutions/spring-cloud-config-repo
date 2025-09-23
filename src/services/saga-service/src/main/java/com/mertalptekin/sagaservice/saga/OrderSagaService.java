package com.mertalptekin.sagaservice.saga;

import com.mertalptekin.sagaservice.event.*;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class OrderSagaService {

    private final StreamBridge streamBridge;

    public OrderSagaService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendCheckStockEvent(CheckStockEvent event) {
        streamBridge.send("checkStockEvent-out-0", event);  // application.yml'de tanımlı
    }

    public void sendMakePaymentEvent(MakePaymentEvent event) {
        streamBridge.send("makePaymentEvent-out-0", event);
    }

    public void sendCompleteOrderEvent(CompleteOrderEvent event) {
        streamBridge.send("completeOrderEvent-out-0", event);
    }

    public void sendRejectOrderEvent(RejectOrderEvent event) {
        streamBridge.send("rejectOrderEvent-out-0", event);
    }

    public void sendReleaseStockEvent(ReleaseStockEvent event) {
        streamBridge.send("releaseStockEvent-out-0", event);
    }
}
