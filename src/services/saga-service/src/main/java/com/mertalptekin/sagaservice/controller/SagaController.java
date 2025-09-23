package com.mertalptekin.sagaservice.controller;


import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/saga")
public class SagaController {

    private StateMachine<String, String> stateMachine;

    public SagaController(StateMachine<String, String> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @PostMapping("/submit")
    public String submitOrder() {

        // Not: Neden bu arkadaşlar deprecated buna bakalım.
        stateMachine.start();
        stateMachine.sendEvent("SUBMIT_ORDER_EVENT");
        stateMachine.sendEvent("CHECK_STOCK_SUCCESS");
        stateMachine.sendEvent("PAY_SUCCESS");

        System.out.println("Order submitted! Current state: " + stateMachine.getState().getId());


        return "Order process triggered!";
    }

}
