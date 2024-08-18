package com.example.tech.service;


import com.example.tech.dto.AuthData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  @Value("${keycloak.clientId}")
  private String clientId;
  @Value("${keycloak.resource.url}")
  private String resourceUrl;
  @Value("${keycloak.grant.type}")
  private String grantType;
  @Value("${keycloak.admin.password}")
  private String adminPassword;
  @Value("${keycloak.admin.user}")
  private String adminUser;
  @Value("${keycloak.admin.url}")
  private String adminUrl;

  public String auth(AuthData authDataDto) {
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("client_id", clientId);
    formData.add("username", authDataDto.username());
    formData.add("password", authDataDto.password());
    formData.add("grant_type", grantType);

    final var requestEntity = new HttpEntity<>(formData, headers);
    final var restTemplate = new RestTemplate();

    final var response =
        restTemplate.exchange(resourceUrl, HttpMethod.POST, requestEntity, String.class);

    if (response.getStatusCode().value() == 200) {
      return response.getBody();
    } else {
      return null;
    }
  }

  String getAdminToken() throws JsonProcessingException {
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("client_id", "admin-cli");
    formData.add("username", adminUser);
    formData.add("password", adminPassword);
    formData.add("grant_type", grantType);

    final var requestEntity = new HttpEntity<>(formData, headers);
    final var restTemplate = new RestTemplate();

    final var response =
        restTemplate.exchange(adminUrl, HttpMethod.POST, requestEntity, String.class);

    if (response.getStatusCode().value() == 200) {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(response.getBody());
      return node.get("access_token").asText();
    } else {
      return null;
    }
  }

}