package com.example.tech.service;

import com.example.tech.model.Article;
import com.example.tech.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;

  public Page<Article> getAllArticles(Pageable pageable) {
    return articleRepository.findAll(pageable);
  }

  public void createNewArticle(Article newArticle) {
    articleRepository.save(newArticle);
  }
}
