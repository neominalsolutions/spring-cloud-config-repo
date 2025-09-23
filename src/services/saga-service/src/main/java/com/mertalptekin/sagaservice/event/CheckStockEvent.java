package com.mertalptekin.sagaservice.event;

public record CheckStockEvent(String orderId, int quantity) { }
