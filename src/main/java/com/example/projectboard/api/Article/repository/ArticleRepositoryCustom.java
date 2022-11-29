package com.example.projectboard.api.Article.repository;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<String> findAllDistinctHashTags();
}
