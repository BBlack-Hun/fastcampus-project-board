package com.example.projectboard.api.Article.payload.response;

import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.hashtag.payload.HashTagDto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ArticleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Set<String> hashTags;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;

    public static ArticleResponse of(Long id, String title, String content, Set<String> hashTags, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashTags, createdAt, email, nickname);
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
                articleDto.getHashTagDtos().stream()
                        .map(HashTagDto::getHashTagName)
                        .collect(Collectors.toSet()),
                articleDto.getCreatedAt(),
                articleDto.getUserAccountDto().getEmail(),
                nickname
        );
    }
}
