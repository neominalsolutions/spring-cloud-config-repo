package com.mertalptekin.sagaservice.controller;



import com.mertalptekin.sagaservice.event.OrderSubmittedEvent;
import com.mertalptekin.sagaservice.saga.OrderSagaService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/saga")
public class SagaController {

    private final OrderSagaService orderSagaService;

    public SagaController(OrderSagaService orderSagaService) {
        this.orderSagaService = orderSagaService;
    }

    @PostMapping("submit")
    public void submitOrder(@RequestBody OrderSubmittedEvent orderSubmittedEvent) {

        this.orderSagaService.sendSubmitOrderEvent(orderSubmittedEvent);
    }

}
