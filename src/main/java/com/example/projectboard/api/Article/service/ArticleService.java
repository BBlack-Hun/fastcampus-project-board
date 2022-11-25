package com.example.projectboard.api.Article.service;

import com.example.projectboard.api.Article.entity.type.SearchType;
import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.Article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType type, String query) {
        return Page.empty();
    }
    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto articleDto) {

    }

    public void updateArticle(long articleId, ArticleDto articleDto) {
    }

    public void deleteArticle(long articleId) {
    }
}
