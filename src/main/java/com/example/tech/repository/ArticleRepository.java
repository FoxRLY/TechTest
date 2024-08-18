package com.example.tech.repository;

import com.example.tech.dto.StatisticsData;
import com.example.tech.model.Article;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

  @Query(nativeQuery = true, value = "select date, count(*) from article group by date having date > :search_date order by date")
  List<StatisticsData> countPostsByDate(@Param("search_date") LocalDate searchDate);
}
