package com.example.projectboard.api.Article.payload;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.hashtag.entity.HashTag;
import com.example.projectboard.api.User.entity.UserAccount;
import com.example.projectboard.api.User.payload.UserAccountDto;
import com.example.projectboard.api.hashtag.payload.HashTagDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link com.example.projectboard.api.Article.entity.Article} entity
 */
@Data
public class ArticleDto{

    private final Long id;
    private final UserAccountDto userAccountDto;
    private final String title;
    private final String content;
    private final Set<HashTagDto> hashTagDtos;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, Set<HashTagDto> hashTagDtos) {
        return new ArticleDto(null, userAccountDto, title, content, hashTagDtos, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, Set<HashTagDto> hashTagDtos,
                                LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashTagDtos, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
            entity.getId(),
            UserAccountDto.from(entity.getUserAccount()),
            entity.getTitle(),
            entity.getContent(),
            entity.getHashTags().stream()
                    .map(HashTagDto::from)
                    .collect(Collectors.toSet()),
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getModifiedAt(),
            entity.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content
        );
    }
}
