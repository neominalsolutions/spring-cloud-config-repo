package com.mertalptekin.sagaservice.saga;

import com.mertalptekin.sagaservice.event.*;
import org.springframework.stereotype.Service;

@Service
public class OrderSagaHandler {

    private final OrderSagaService sagaService;

    public OrderSagaHandler(OrderSagaService sagaService) {
        this.sagaService = sagaService;
    }

    // Order Submitted Event alındığında stok kontrolü için CheckStockEvent fırlatılır.
    public void handleOrderSubmitted(OrderSubmittedEvent event) {
        var checkStockEvent = new CheckStockEvent(event.orderId(), event.code(), event.quantity());

        System.out.println("handleOrderSubmitted " + event);

        this.sagaService.sendCheckStockEvent(checkStockEvent);
    }

    // Stok rezervasyonu başarılı ise ödeme işlemi için MakePaymentEvent fırlatılır.
    public void handleStockReserved(StockReservedEvent event) {

        Double amount = Math.random() * 100;

        var makePaymentEvent = new MakePaymentEvent(event.orderId(), event.code(),amount);
        System.out.println("Ödenecek tutar " + amount);
        System.out.println("handleStockReserved " + event);

        this.sagaService.sendMakePaymentEvent(makePaymentEvent);
    }

    // Stok rezervasyonu başarısız ise siparişi reddetmek için RejectOrderEvent fırlatılır.
    public void handleStockNotAvailable(StockNotAvailableEvent event) {

        System.out.println("handleStockNotAvailable " + event);
        var rejectEvent = new RejectOrderEvent(event.orderId(),"Stock not available");
        this.sagaService.sendRejectOrderEvent(rejectEvent);

    }

    // Ödeme başarılı ise siparişi tamamlamak için CompleteOrderEvent fırlatılır.
    public void handlePaymentSucceeded(PaidSucceededEvent event) {
        var completeEvent = new CompleteOrderEvent(event.orderId());

        System.out.println("handlePaymentSucceeded " + completeEvent);

        this.sagaService.sendCompleteOrderEvent(completeEvent);
    }

    // Ödeme başarısız ise stok rezervasyonunu geri almak için ReleaseStockEvent ve siparişi reddetmek için RejectOrderEvent fırlatılır.
    public void handlePaymentFailed(PaidFailedEvent event) {

        System.out.println("handlePaymentFailed " + event);

        var releaseEvent = new ReleaseStockEvent(event.code(),event.orderId());
        this.sagaService.sendReleaseStockEvent(releaseEvent);

        var rejectEvent = new RejectOrderEvent(event.orderId(),"Payment failed");
        this.sagaService.sendRejectOrderEvent(rejectEvent);
    }
}