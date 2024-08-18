package com.example.tech.controller;

import com.example.tech.dto.AuthData;
import com.example.tech.service.AuthService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  String login(@RequestBody AuthData authDataDto) {
    return authService.auth(authDataDto);
  }

  @GetMapping("/test-user")
  String testUser(Principal principal) {
    return "You are a User " + principal.getName();
  }

  @GetMapping("/test-admin")
  String testAdmin() {
    return "You are an Admin";
  }
}
