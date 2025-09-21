package com.mertalptekin.messaging.model;

public record SubmitOrderMessage(String id, String product, int quantity) {}