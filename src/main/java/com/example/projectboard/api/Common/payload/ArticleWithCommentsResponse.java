package com.example.projectboard.api.Common.payload;

import com.example.projectboard.api.AritlcleCommend.payload.ArticleCommentResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ArticleWithCommentsResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String hashTag;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;
    private final String userId;
    private final Set<ArticleCommentResponse> articleCommentResponses;


    public static ArticleWithCommentsResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname, String userId, Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentsResponse(id, title, content, hashtag, createdAt, email, nickname, userId, articleCommentResponses);
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto articleWithCommentsDto) {
        String nickname = articleWithCommentsDto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isEmpty() || nickname.equals(" ")) {
            nickname = articleWithCommentsDto.getUserAccountDto().getUserId();
        }

        return new ArticleWithCommentsResponse(
                articleWithCommentsDto.getId(),
                articleWithCommentsDto.getTitle(),
                articleWithCommentsDto.getContent(),
                articleWithCommentsDto.getHashTag(),
                articleWithCommentsDto.getCreatedAt(),
                articleWithCommentsDto.getUserAccountDto().getEmail(),
                nickname,
                articleWithCommentsDto.getUserAccountDto().getUserId(),
                articleWithCommentsDto.getArticleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
