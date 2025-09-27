package com.mertalptekin.productservice.controller;


import com.mertalptekin.productservice.model.GetOrderedProduct;
import com.mertalptekin.productservice.service.OrderService;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("test-distributed-tracing")
    public String orderedProducts() {
        GetOrderedProduct model = new GetOrderedProduct();
        model.setId(1);
        model.setName("test");
        return orderService.getOrderedProducts(model);
    }


    @PostMapping("test-distributed-tracing")
    public String postorderedProducts(@RequestBody GetOrderedProduct model) {
        return orderService.getOrderedProducts(model);
    }

    @TimeLimiter(name = "orderService", fallbackMethod = "fallbackTimeout")
    @GetMapping("test-timelimiter")
    public CompletableFuture<String> getOrderedProductsAsync(GetOrderedProduct model) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate long running task
                Thread.sleep(8000);
                return orderService.getOrderedProducts(new GetOrderedProduct());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> fallbackTimeout(GetOrderedProduct model, Throwable t)
    {
        return CompletableFuture.completedFuture("Timeout fallback triggered: " + t.getMessage());
    }



    // Not: Tek seferde 5 paralel istek yapıyor
    @GetMapping("test-bulkhead")
    public List<String> testBulkhead() throws Exception {
        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                return orderService.getOrderedProducts(new GetOrderedProduct());
            }));
        }

        // Tüm future'lar tamamlanana kadar bekle
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Sonuçları topla
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }



}
