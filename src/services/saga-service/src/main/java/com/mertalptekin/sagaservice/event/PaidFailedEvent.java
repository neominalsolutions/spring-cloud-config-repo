package com.mertalptekin.sagaservice.event;

public record PaidFailedEvent(String stockCode,String orderId) { }
