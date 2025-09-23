package com.mertalptekin.sagaservice.event;

public record MakePaymentEvent(String orderId,double amount) {
}
