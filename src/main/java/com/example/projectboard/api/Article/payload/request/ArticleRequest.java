package com.example.projectboard.api.Article.payload.request;

import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.User.payload.UserAccountDto;
import lombok.Data;

@Data
public class ArticleRequest {

    private final String title;
    private final String content;
    private final String hashTag;

    public static ArticleRequest of(String title, String content, String hashTag) {
        return new ArticleRequest(title, content, hashTag);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashTag
        );
    }
}
