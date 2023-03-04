package com.example.projectboard.api.Article.payload.request;

import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.User.payload.UserAccountDto;
import com.example.projectboard.api.hashtag.payload.HashTagDto;
import lombok.Data;

import java.util.Set;

@Data
public class ArticleRequest {

    private final String title;
    private final String content;

    public static ArticleRequest of(String title, String content) {
        return new ArticleRequest(title, content);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return toDto(userAccountDto, null);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto, Set<HashTagDto> hashTagDtos) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashTagDtos
        );
    }
}
