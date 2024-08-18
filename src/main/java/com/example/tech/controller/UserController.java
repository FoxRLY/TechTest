package com.example.tech.controller;

import com.example.tech.dto.UserCreationData;
import com.example.tech.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/user/create")
  String createUser(@RequestBody UserCreationData userCreationData) throws JsonProcessingException {
    return userService.createUser(userCreationData);
  }
}
