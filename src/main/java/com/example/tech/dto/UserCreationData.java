package com.example.tech.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserCreationData {
  String username;
  String password;
  String email;
  String firstName;
  String lastName;
}
