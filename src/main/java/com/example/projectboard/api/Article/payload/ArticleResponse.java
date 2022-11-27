package com.example.projectboard.api.Article.payload;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ArticleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String hashTag;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;

    public static ArticleResponse of(Long id, String title, String content, String hashTag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashTag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto articleDto) {
        String nickname = articleDto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isEmpty() || nickname.equals(" ")) {
            nickname = articleDto.getUserAccountDto().getUserId();
        }

        return new ArticleResponse(
                articleDto.getId(),
                articleDto.getTitle(),
                articleDto.getContent(),
                articleDto.getHashTag(),
                articleDto.getCreatedAt(),
                articleDto.getUserAccountDto().getEmail(),
                nickname
        );
    }
}
