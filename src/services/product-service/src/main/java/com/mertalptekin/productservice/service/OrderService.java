package com.mertalptekin.productservice.service;



import com.mertalptekin.productservice.client.OrderClient;
import com.mertalptekin.productservice.model.GetOrderedProduct;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    private final OrderClient orderClient;
    private final Tracer tracer;

    public OrderService(OrderClient orderClient, Tracer tracer) {
        this.orderClient = orderClient;
        this.tracer = tracer;
    }


    // Not: Bu iki anatasyon üstüste konursa önce retry denenir.

    // Not: Varsayılan çalışma sırası (default order) Resilience4j tarafından belirlenir ve sabittir.
    // Client çağrısı → RateLimiter → Bulkhead → TimeLimiter → CircuitBreaker → Retry → Asıl method

    // @Retry(name = "orderService",fallbackMethod = "fallbackRetry")
    // @CircuitBreaker(name = "orderService", fallbackMethod = "fallbackCircuitBraker")
    @Bulkhead(name = "orderService",fallbackMethod = "fallbackBulkHead")
    // @RateLimiter(name = "orderService",fallbackMethod = "rateLimitFallback")
    public String getOrderedProducts(GetOrderedProduct model) {
     
      // Span span = tracer.nextSpan().name("OrderService.getOrderedProducts").start();
       var result = orderClient.getOrderedProducts();
       //span.end();

        return result;
    }

    public String fallbackCircuitBraker(GetOrderedProduct model, Throwable t) {
        return "CircuitBreaker fallback triggered: " + t.getMessage();
    }

    public String fallbackRetry(GetOrderedProduct model, Throwable t) {
        return "Retry fallback triggered: " + t.getMessage();
    }



    public String fallbackBulkHead(GetOrderedProduct model, Throwable t) {
        return "fallbackBulkHead fallback triggered: " + t.getMessage();
    }

    public String rateLimitFallback(GetOrderedProduct model, RequestNotPermitted ex) {
        return "Rate limit aşıldı, lütfen daha sonra tekrar deneyin.";
    }
}
