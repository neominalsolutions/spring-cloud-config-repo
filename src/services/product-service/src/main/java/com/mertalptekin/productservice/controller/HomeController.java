package com.mertalptekin.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class HomeController {

    @Value("${server.port}")
    private String port;


    @GetMapping
    public  String get() {
        return   ", Product Service Port: " + port;
    }

}
