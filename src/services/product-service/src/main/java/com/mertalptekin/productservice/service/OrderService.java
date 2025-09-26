package com.mertalptekin.productservice.service;



import com.mertalptekin.productservice.client.OrderClient;
import com.mertalptekin.productservice.model.GetOrderedProduct;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderClient orderClient;
    private final Tracer tracer;

    public OrderService(OrderClient orderClient, Tracer tracer) {
        this.orderClient = orderClient;
        this.tracer = tracer;
    }


    public String getOrderedProducts(GetOrderedProduct model) {

//        Span span = tracer.nextSpan().name("OrderService.getOrderedProducts").start();

       var result = orderClient.getOrderedProducts();

//       span.end();


        return result;
    }
}
