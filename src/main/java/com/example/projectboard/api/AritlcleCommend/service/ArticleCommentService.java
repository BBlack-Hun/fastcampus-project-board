package com.example.projectboard.api.AritlcleCommend.service;

import com.example.projectboard.api.AritlcleCommend.payload.ArticleCommentDto;
import com.example.projectboard.api.AritlcleCommend.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return Collections.emptyList();
    }

    public void saveArticleComment(ArticleCommentDto articleCommentDto) {
    }

    public void updateArticleComment(ArticleCommentDto articleCommentDto) {

    }

    public void deleteArticleComment(Long ArticleCommentId) {

    }
}
