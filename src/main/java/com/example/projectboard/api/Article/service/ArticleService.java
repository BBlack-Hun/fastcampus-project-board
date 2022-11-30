package com.example.projectboard.api.Article.service;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Article.entity.constant.SearchType;
import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.Article.repository.ArticleRepository;
import com.example.projectboard.api.Common.payload.ArticleWithCommentsDto;
import com.example.projectboard.api.User.entity.UserAccount;
import com.example.projectboard.api.User.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserAccountRepository userAccountRepository;

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
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다. - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(()-> new EntityNotFoundException("게시글이 없습니다. - articleId: " + articleId));

    }

    public void saveArticle(ArticleDto articleDto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(articleDto.getUserAccountDto().getUserId());
        articleRepository.save(articleDto.toEntity(userAccount));
    }

    public void updateArticle(Long articleId, ArticleDto articleDto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
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

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashTag(String hashTag, Pageable pageable) {
        if (hashTag == null || hashTag.isEmpty() || hashTag.equals(" ")) {
            return Page.empty(pageable);
        }
        return articleRepository.findByHashTag(hashTag, pageable).map(ArticleDto::from);
    }

    public List<String> getHashTags() {
        return articleRepository.findAllDistinctHashTags();
    }
}
