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
        public Consumer<OrderSubmittedEvent> orderSubmitted() {
            return sagaHandler::handleOrderSubmitted;
        }

        @Bean
        public Consumer<StockReservedEvent> stockReserved() {
            return sagaHandler::handleStockReserved;
        }

        @Bean
        public Consumer<StockNotAvailableEvent> stockNotAvailable() {
            return sagaHandler::handleStockNotAvailable;
        }

        @Bean
        public Consumer<PaidSucceededEvent> paidSucceeded() {
            return sagaHandler::handlePaymentSucceeded;
        }

        @Bean
        public Consumer<PaidFailedEvent> paidFailed() {
            return sagaHandler::handlePaymentFailed;
        }

        // Burada checkStockEvent-in-0 ve diğerlerine ihtiyaç olabilir.
    }


