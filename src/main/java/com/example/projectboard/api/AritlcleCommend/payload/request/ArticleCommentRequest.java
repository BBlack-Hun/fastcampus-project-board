package com.example.projectboard.api.AritlcleCommend.payload.request;

import com.example.projectboard.api.AritlcleCommend.payload.ArticleCommentDto;
import com.example.projectboard.api.User.payload.UserAccountDto;
import lombok.Data;

@Data
public class ArticleCommentRequest {

    private final Long articleId;

    private final String content;

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
