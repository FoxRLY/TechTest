package com.example.tech.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@Builder(toBuilder = true)
public class UserCredentials {
  String value;
  boolean temporary;
  String type;
}
