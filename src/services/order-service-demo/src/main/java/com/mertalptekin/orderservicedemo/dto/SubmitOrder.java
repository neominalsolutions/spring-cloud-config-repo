package com.mertalptekin.orderservicedemo.dto;

import java.math.BigDecimal;

public record SubmitOrder(String product, Integer quantity, BigDecimal price, String status) {};
