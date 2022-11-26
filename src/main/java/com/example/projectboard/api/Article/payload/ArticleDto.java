package com.example.projectboard.api.Article.payload;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.User.payload.UserAccountDto;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.example.projectboard.api.Article.entity.Article} entity
 */
@Data
public class ArticleDto{

    private final Long id;
    private final UserAccountDto userAccountDto;
    private final String title;
    private final String content;
    private final String hashTag;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashTag,
                                LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashTag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
            entity.getId(),
            UserAccountDto.from(entity.getUserAccount()),
            entity.getTitle(),
            entity.getContent(),
            entity.getHashTag(),
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getModifiedAt(),
            entity.getModifiedBy()
        );
    }

    public Article toEntity() {
        return Article.of(
                userAccountDto.toEntity(),
                title,
                content,
                hashTag
        );
    }
}
