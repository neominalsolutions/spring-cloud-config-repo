package com.mertalptekin.sagaservice.saga;

import com.mertalptekin.sagaservice.event.*;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class OrderSagaHandler {

    private final StreamBridge streamBridge;

    public OrderSagaHandler(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void handleOrderSubmitted(OrderSubmittedEvent event) {
        var checkStock = new CheckStockEvent(event.orderId(),5);
        streamBridge.send("checkStockEvent-out-0", checkStock);
    }

    public void handleStockReserved(StockReservedEvent event) {
        var makePayment = new MakePaymentEvent(event.orderId(),500);
        streamBridge.send("payment-out-0", makePayment);
    }

    public void handleStockNotAvailable(StockNotAvailableEvent event) {
        var reject = new RejectOrderEvent(event.orderId(),"Stock not available");
        streamBridge.send("order-out-0", reject);
    }

    public void handlePaymentSucceeded(PaidSucceededEvent event) {
        var complete = new CompleteOrderEvent(event.orderId());
        streamBridge.send("order-out-0", complete);
    }

    public void handlePaymentFailed(PaidFailedEvent event) {
        var release = new ReleaseStockEvent(event.stockCode(),event.orderId());
        streamBridge.send("inventory-out-0", release);

        var reject = new RejectOrderEvent(event.orderId(),"Payment failed");
        streamBridge.send("order-out-0", reject);
    }
}