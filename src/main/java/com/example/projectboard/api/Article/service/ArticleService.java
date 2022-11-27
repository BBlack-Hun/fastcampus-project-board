package com.example.projectboard.api.Article.service;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Article.entity.type.SearchType;
import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.Article.repository.ArticleRepository;
import com.example.projectboard.api.Common.payload.ArticleWithCommentsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isEmpty() || searchKeyword.equals(" ")) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        switch (searchType) {
            case TITLE:
                return articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT:
                return articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID:
                return articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME:
                return articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG:
                return articleRepository.findByHashTag("#" + searchKeyword, pageable).map(ArticleDto::from);
        }

        return null;
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다. - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto articleDto) {
        articleRepository.save(articleDto.toEntity());
    }

    public void updateArticle(ArticleDto articleDto) {
        try {
            Article article = articleRepository.getReferenceById(articleDto.getId());
            if (article.getTitle() != null) {
                article.setTitle(articleDto.getTitle());
            }
            if (article.getContent() != null) {
                article.setContent(articleDto.getContent());
            }
            article.setHashTag(articleDto.getHashTag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", articleDto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }
}
