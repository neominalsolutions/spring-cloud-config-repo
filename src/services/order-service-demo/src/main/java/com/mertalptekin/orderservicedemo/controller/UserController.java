package com.mertalptekin.orderservicedemo.controller;


import com.mertalptekin.orderservicedemo.client.UserClient;
import com.mertalptekin.orderservicedemo.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private  final UserClient userClient;

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {

        var response = this.userClient.getUsers();


        return ResponseEntity.ok(response);
    }
}
