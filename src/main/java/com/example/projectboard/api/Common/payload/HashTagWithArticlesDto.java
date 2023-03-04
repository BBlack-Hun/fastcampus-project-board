package com.example.projectboard.api.Common.payload;

import com.example.projectboard.api.AritlcleCommend.payload.ArticleCommentDto;
import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.User.payload.UserAccountDto;
import com.example.projectboard.api.hashtag.entity.HashTag;
import com.example.projectboard.api.hashtag.payload.HashTagDto;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class HashTagWithArticlesDto {

    private final Long id;
    private final Set<ArticleDto> articles;
    private final String hashTagName;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static HashTagWithArticlesDto of(Set<ArticleDto> articles, String hashTagName) {
        return new HashTagWithArticlesDto(null, articles, hashTagName, null, null, null, null);
    }

    public static HashTagWithArticlesDto of(Long id, Set<ArticleDto> articles, String hashTagName,
        String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt,
        String modifiedBy) {
        return new HashTagWithArticlesDto(id, articles, hashTagName, createdAt, createdBy,
            modifiedAt, modifiedBy);
    }

    public static HashTagWithArticlesDto from(HashTag entity) {
        return new HashTagWithArticlesDto(
            entity.getId(),
            entity.getArticles().stream()
                .map(ArticleDto::from)
                .collect(Collectors.toSet())
            ,
            entity.getHashTagName(),
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getModifiedAt(),
            entity.getModifiedBy()
        );
    }

    public HashTag toEntity() {
        return HashTag.of(hashTagName);
    }
}
