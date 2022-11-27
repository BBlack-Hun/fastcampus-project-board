package com.example.projectboard.api.AritlcleCommend.payload;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ArticleCommentResponse {

    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;

    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleCommentResponse(id, content, createdAt, email, nickname);
    }

    public static ArticleCommentResponse from(ArticleCommentDto articleCommentDto) {
        String nickname = articleCommentDto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isEmpty() || nickname.equals(" ")) {
            nickname = articleCommentDto.getUserAccountDto().getUserId();
        }

        return new ArticleCommentResponse(
                articleCommentDto.getId(),
                articleCommentDto.getContent(),
                articleCommentDto.getCreatedAt(),
                articleCommentDto.getUserAccountDto().getEmail(),
                nickname
        );
    }
}
