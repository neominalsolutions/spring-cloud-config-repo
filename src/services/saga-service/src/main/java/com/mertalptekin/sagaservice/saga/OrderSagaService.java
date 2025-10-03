package com.mertalptekin.sagaservice.saga;

import com.mertalptekin.sagaservice.event.*;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


// İşlemin kaldığı yerden devam etmesi için eventleri publish ederek -> süreci kaldığı yerden devam ettirebiliriz.


@Service
public class OrderSagaService {

    private final StreamBridge streamBridge;

    public OrderSagaService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    // 1. Adım Order Submit edildiğinde
    public void sendSubmitOrderEvent(OrderSubmittedEvent event) {
        System.out.println("sendSubmitOrderEvent " + event);
        streamBridge.send("orderSubmitEvent-out-0", event);  // application.yml'de tanımlı
    }

    // 2. Adım Stok kontrolü
    public void sendCheckStockEvent(CheckStockEvent event) {
        System.out.println("sendCheckStockEvent: " + event);
        streamBridge.send("checkStockEvent-out-0", event);  // application.yml'de tanımlı
    }

    // 3. Adım Ödeme işlemi
    public void sendMakePaymentEvent(MakePaymentEvent event) {
        System.out.println("sendMakePaymentEvent: " + event);
        streamBridge.send("makePaymentEvent-out-0", event);
    }

    // 4.Adım Sipariş işlemi tamamlandı, Başarılı yada başarısız
    // Finish adımı
    public void sendCompleteOrderEvent(CompleteOrderEvent event) {
        System.out.println("sendCompleteOrderEvent: " + event);
        streamBridge.send("completeOrderEvent-out-0", event);
    }

    // Finish adımı ama Order Completed olmadı
    public void sendRejectOrderEvent(RejectOrderEvent event) {
        System.out.println("sendRejectOrderEvent: " + event);
        streamBridge.send("rejectOrderEvent-out-0", event);
    }

    // Eğer storck reserve ettiksek geri alma compensating transaction
    public void sendReleaseStockEvent(ReleaseStockEvent event) {
        System.out.println("sendReleaseStockEvent: " + event);
        streamBridge.send("releaseStockEvent-out-0", event);
    }
}
