package com.example.projectboard.api.AritlcleCommend.service;

import com.example.projectboard.api.AritlcleCommend.payload.ArticleCommentDto;
import com.example.projectboard.api.AritlcleCommend.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return Arrays.asList();
    }

    public void saveArticleComment(ArticleCommentDto articleCommentDto) {
    }
}
