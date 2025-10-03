package com.mertalptekin.sagaservice.saga;


import com.mertalptekin.sagaservice.event.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
    public class OrderSagaConsumer {

        private final OrderSagaHandler sagaHandler;


        public OrderSagaConsumer(OrderSagaHandler sagaHandler) {
            this.sagaHandler = sagaHandler;
        }

        @Bean
        public Consumer<OrderSubmittedEvent> orderSubmitEvent() {
            return sagaHandler::handleOrderSubmitted;
        }

        // Inventory Service Consumer
        @Bean
        public Consumer<CheckStockEvent> checkStockEvent() {

            long availableStock = Math.round(Math.random() * 100);

            return  event -> {
                if(event.quantity() > availableStock) {
                    sagaHandler.handleStockNotAvailable(new StockNotAvailableEvent(event.orderId(),event.code()));

                } else {
                    sagaHandler.handleStockReserved(new StockReservedEvent(event.orderId(),event.code(),event.quantity()));
                }
            };
        }

        // Payment Service Consumer
        @Bean
        public  Consumer<MakePaymentEvent> makePaymentEvent() {
            return event -> {
                double balance = Math.random() * 100; // bakiye
                System.out.println("bakiye :" + balance);
                System.out.println("ödenecek tutar :" + event.amount());
                if(event.amount() > balance){ // ödenecek tutar bakiyeden fazla ise limit yetersiz
                    sagaHandler.handlePaymentFailed(new PaidFailedEvent(event.orderId(),event.code(),"Bakiye Yetersiz"));
                } else {
                    sagaHandler.handlePaymentSucceeded(new PaidSucceededEvent(event.orderId(),"Ödeme alındı"));
                }
            };
        }

        // Eğer Inventory serviste stockReservedEvent dinlersem bu durumda nasıl bir step den devam eceğime karar verebilirim.
    // Not: Saga Servisler karar adımlarını uygularken replay channeldan dönen eventleri dinleyerek karar verebilir.
        @Bean
        public Consumer<StockReservedEvent> stockReservedEvent() {
            return sagaHandler::handleStockReserved;
        }

        @Bean
        public Consumer<StockNotAvailableEvent> stockNotAvailableEvent() {
            return sagaHandler::handleStockNotAvailable;
        }

        @Bean
        public Consumer<PaidSucceededEvent> paidSucceededEvent() {
            return sagaHandler::handlePaymentSucceeded;
        }

        @Bean
        public Consumer<PaidFailedEvent> paidFailedEvent() {
            return sagaHandler::handlePaymentFailed;
        }

        // Burada checkStockEvent-in-0 ve diğerlerine ihtiyaç olabilir.
    }


