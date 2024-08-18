package com.example.tech.controller;

import com.example.tech.model.Article;
import com.example.tech.repository.ArticleRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.security.Principal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
public class ArticleControllerTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:16-alpine"
  );

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  private ArticleController articleController;

  @Autowired
  private ArticleRepository articleRepository;


  @Test
  void testGetArticles() {
    Article article = new Article();
    article.setTitle("Test Article");
    article.setContent("This is a test article.");
    article.setAuthor(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    articleRepository.save(article);

    PagedModel<Article> response = articleController.getArticles(Pageable.ofSize(10));

    assertEquals(response.getContent().size(), 1);
    assertEquals(response.getContent().get(0).getTitle(), "Test Article");
  }

  @Test
  void testCreateArticle() {
    Principal principal = () -> "testUser";

    Article newArticle = new Article();
    newArticle.setTitle("Test Article");
    newArticle.setContent("This is a test article.");
    newArticle.setAuthor(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

    articleController.createArticle(newArticle, principal);

    assertEquals(1, articleRepository.count());
  }
}