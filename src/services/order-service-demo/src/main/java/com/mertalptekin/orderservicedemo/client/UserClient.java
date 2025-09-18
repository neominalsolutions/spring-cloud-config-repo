package com.mertalptekin.orderservicedemo.client;


import com.mertalptekin.orderservicedemo.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

     @GetMapping("/api/v1/users")
     List<UserDto> getUsers();

}
