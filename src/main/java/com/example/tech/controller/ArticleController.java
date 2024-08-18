package com.example.tech.controller;

import com.example.tech.dto.StatisticsData;
import com.example.tech.model.Article;
import com.example.tech.repository.ArticleRepository;
import com.example.tech.service.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
  private final ArticleService articleService;
  private final ArticleRepository articleRepository;

  @GetMapping("/list")
  PagedModel<Article> getArticles(@NotNull Pageable page) {
    return new PagedModel<>(articleService.getAllArticles(page));
  }

  @PostMapping("/create")
  void createArticle(@RequestBody @Valid Article newArticle, Principal principal) {
    newArticle.setAuthor(UUID.fromString(principal.getName()));
    articleService.createNewArticle(newArticle);
  }

  @GetMapping("/statistics")
  List<StatisticsData> getStatistics() {
    log.info(LocalDate.now().minusDays(7).toString());
    return articleRepository.countPostsByDate(LocalDate.now().minusDays(7));
  }
}
