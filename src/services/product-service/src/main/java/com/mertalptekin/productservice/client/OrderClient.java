package com.mertalptekin.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="order-service")
public interface OrderClient {

    @GetMapping("/api/v1/orderedProducts")
    String getOrderedProducts();


}
