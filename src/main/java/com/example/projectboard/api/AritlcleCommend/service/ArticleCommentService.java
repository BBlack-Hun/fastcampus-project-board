package com.example.projectboard.api.AritlcleCommend.service;

import com.example.projectboard.api.AritlcleCommend.entity.ArticleComment;
import com.example.projectboard.api.AritlcleCommend.payload.ArticleCommentDto;
import com.example.projectboard.api.AritlcleCommend.repository.ArticleCommentRepository;
import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.Article.payload.ArticleResponse;
import com.example.projectboard.api.Article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .collect(Collectors.toList());
    }

    public void saveArticleComment(ArticleCommentDto articleCommentDto) {
        try {
            articleCommentRepository.save(articleCommentDto.toEntity(articleRepository.getReferenceById(articleCommentDto.getArticleId())));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글의 게시글 을 찾을 수 없습니다. - dto: {}", articleCommentDto);
        }
    }

    public void updateArticleComment(ArticleCommentDto articleCommentDto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(articleCommentDto.getId());
            if (articleCommentDto.getContent() != null) {
                articleComment.setContent(articleCommentDto.getContent());
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다. - dto: {}", articleCommentDto);
        }

    }

    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }
}
