package com.example.tech.service;

import com.example.tech.dto.AdminAuth;
import com.example.tech.dto.UserCreationData;
import com.example.tech.dto.UserCredentials;
import com.example.tech.feign.AdminAuthFeign;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final AdminAuthFeign adminAuthFeign;
  private final AuthService authService;

  public String createUser(UserCreationData userCreationData) throws JsonProcessingException {
    return adminAuthFeign.createUser("Bearer " + authService.getAdminToken()
        , AdminAuth.builder()
            .username(userCreationData.getUsername())
            .email(userCreationData.getEmail())
            .emailVerified(true)
            .enabled(true)
            .credentials(List.of(UserCredentials.builder()
                .value(userCreationData.getPassword())
                .temporary(false)
                .type("password")
                .build() ))
            .build());
  }
}
