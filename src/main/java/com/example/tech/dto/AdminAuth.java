package com.example.tech.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class AdminAuth {
  String email;
  boolean emailVerified;
  boolean enabled;
  List<String> realmRoles;
  String username;
  List<UserCredentials> credentials;
}
