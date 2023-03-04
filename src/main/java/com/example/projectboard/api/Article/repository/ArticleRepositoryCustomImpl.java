package com.example.projectboard.api.Article.repository;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Article.entity.QArticle;
import com.example.projectboard.api.hashtag.entity.QHashTag;
import com.querydsl.jpa.JPQLQuery;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
                .select(article.hashTags.any().hashTagName)
                .fetch();

    }

    @Override
    public Page<Article> findByHashTagNames(Collection<String> hashTagNames, Pageable pageable) {
        QHashTag hashtag = QHashTag.hashTag;
        QArticle article = QArticle.article;

        JPQLQuery<Article> query = from(article)
            .innerJoin(article.hashTags, hashtag)
            .where(hashtag.hashTagName.in(hashTagNames));
        List<Article> articles = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(articles, pageable, query.fetchCount());
    }
}
