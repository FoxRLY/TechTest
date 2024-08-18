package com.example.tech.config;

import com.example.tech.util.AuthoritiesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  @Order(1)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).oauth2ResourceServer(
            resourceServer -> resourceServer.jwt(
                jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(
                    keycloakAuthenticationConverter()
                )
            )
        )
        .oauth2Login(Customizer.withDefaults())
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/user/create", "/oauth2/**").permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/test-user").hasRole("User")
                .requestMatchers("/auth/test-admin").hasRole("Admin")
                .requestMatchers("/article/**").hasRole("User")
                .anyRequest().authenticated()
        )
        .build();
  }

  private Converter<Jwt, ? extends AbstractAuthenticationToken> keycloakAuthenticationConverter() {
    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(
        new AuthoritiesConverter());
    return converter;
  }

}
