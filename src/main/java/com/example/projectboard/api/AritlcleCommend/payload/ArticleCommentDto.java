package com.example.projectboard.api.AritlcleCommend.payload;

import com.example.projectboard.api.AritlcleCommend.entity.ArticleComment;
import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.User.entity.UserAccount;
import com.example.projectboard.api.User.payload.UserAccountDto;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.example.projectboard.api.AritlcleCommend.entity.ArticleComment} entity
 */
@Data
public class ArticleCommentDto {

    private final Long id;
    private final Long ArticleId;
    private final UserAccountDto userAccountDto;
    private final String content;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;


    public static ArticleCommentDto of(Long id, Long ArticleId, UserAccountDto userAccountDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentDto(id, ArticleId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public ArticleComment toEntity(Article entity) {
        return ArticleComment.of(
                entity,
                userAccountDto.toEntity(),
                content
        );
    }
}