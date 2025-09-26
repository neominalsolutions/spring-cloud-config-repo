package com.mertalptekin.productservice.controller;


import com.mertalptekin.productservice.model.GetOrderedProduct;
import com.mertalptekin.productservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String orderedProducts() {
        GetOrderedProduct model = new GetOrderedProduct();
        model.setId(1);
        model.setName("test");
        return orderService.getOrderedProducts(model);
    }


    @PostMapping("create")
    public String postorderedProducts(@RequestBody GetOrderedProduct model) {
        return orderService.getOrderedProducts(model);
    }

}
