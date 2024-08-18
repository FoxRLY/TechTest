package com.example.tech.feign;

import com.example.tech.dto.AdminAuth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "Auth", url = "http://localhost:8080/admin/realms/Tech")
public interface AdminAuthFeign {
  @PostMapping("/users")
  String createUser(@RequestHeader("Authorization") String token, @RequestBody AdminAuth adminAuth);
}
