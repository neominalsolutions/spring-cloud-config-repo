package com.mertalptekin.sagaservice.saga;

import com.mertalptekin.sagaservice.event.*;
import org.springframework.stereotype.Service;

@Service
public class OrderSagaHandler {

    private final OrderSagaService sagaService;

    public OrderSagaHandler(OrderSagaService sagaService) {
        this.sagaService = sagaService;
    }

    public void handleOrderSubmitted(OrderSubmittedEvent event) {
        var checkStockEvent = new CheckStockEvent(event.orderId(), event.code(), event.quantity());

        System.out.println("handleOrderSubmitted " + event);

        this.sagaService.sendCheckStockEvent(checkStockEvent);
    }

    public void handleStockReserved(StockReservedEvent event) {

        Double amount = Math.random() * 100;

        var makePaymentEvent = new MakePaymentEvent(event.orderId(), event.code(),amount);
        System.out.println("Ödenecek tutar " + amount);
        System.out.println("handleStockReserved " + event);

        this.sagaService.sendMakePaymentEvent(makePaymentEvent);
    }

    public void handleStockNotAvailable(StockNotAvailableEvent event) {

        System.out.println("handleStockNotAvailable " + event);
        var rejectEvent = new RejectOrderEvent(event.orderId(),"Stock not available");
        this.sagaService.sendRejectOrderEvent(rejectEvent);

    }

    public void handlePaymentSucceeded(PaidSucceededEvent event) {
        var completeEvent = new CompleteOrderEvent(event.orderId());

        System.out.println("handlePaymentSucceeded " + completeEvent);

        this.sagaService.sendCompleteOrderEvent(completeEvent);
    }

    public void handlePaymentFailed(PaidFailedEvent event) {

        System.out.println("handlePaymentFailed " + event);

        var releaseEvent = new ReleaseStockEvent(event.code(),event.orderId());
        this.sagaService.sendReleaseStockEvent(releaseEvent);

        var rejectEvent = new RejectOrderEvent(event.orderId(),"Payment failed");
        this.sagaService.sendRejectOrderEvent(rejectEvent);
    }
}