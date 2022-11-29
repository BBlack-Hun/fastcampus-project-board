package com.example.projectboard.api.Article.repository;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Article.entity.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashTags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct()
                .select(article.hashTag)
                .where(article.hashTag.isNotNull())
                .fetch();

    }
}
