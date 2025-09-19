package com.mertalptekin.userservicedemo.controller;

import com.mertalptekin.userservicedemo.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {


    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){

        List<UserDto> users = new ArrayList<>();
        var user1 = new UserDto();
        user1.setEmail("ali@test.com");
        user1.setUsername("ali");

        var user2 = new UserDto();
        user2.setEmail("ahmet@test.com");
        user2.setUsername("ahmet");


        users.add(user1);
        users.add(user2);


        return ResponseEntity.ok(users);
    }
}