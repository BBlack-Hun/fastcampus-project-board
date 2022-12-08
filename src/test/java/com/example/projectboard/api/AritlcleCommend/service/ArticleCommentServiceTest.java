package com.example.projectboard.api.AritlcleCommend.service;

import com.example.projectboard.api.AritlcleCommend.entity.ArticleComment;
import com.example.projectboard.api.AritlcleCommend.payload.ArticleCommentDto;
import com.example.projectboard.api.AritlcleCommend.repository.ArticleCommentRepository;
import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Article.repository.ArticleRepository;
import com.example.projectboard.api.User.entity.UserAccount;
import com.example.projectboard.api.User.payload.UserAccountDto;
import com.example.projectboard.api.User.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private UserAccountRepository userAccountRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComment8s_thenReturnsArticleComments() {

        // Given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");

        given(articleCommentRepository.findByArticle_id(articleId)).willReturn(Collections.singletonList(expected));

        // When
        List<ArticleCommentDto> actual =  sut.searchArticleComments(articleId);

        // Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_id(articleId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSaveArticleComment() {

        // Given
        ArticleCommentDto articleCommentDto = createArticleCommentDto("댓글");

        given(articleRepository.getReferenceById(articleCommentDto.getArticleId())).willReturn(createArticle());
        given(userAccountRepository.getReferenceById(articleCommentDto.getUserAccountDto().getUserId())).willReturn(createUserAccount());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.saveArticleComment(articleCommentDto);

        // Then
        then(articleRepository).should().getReferenceById(articleCommentDto.getArticleId());
        then(userAccountRepository).should().getReferenceById(articleCommentDto.getUserAccountDto().getUserId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면 경고 로그를 찍고 아무것도 안한다.")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
        // Given
        ArticleCommentDto articleCommentDto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(articleCommentDto.getArticleId())).willThrow(EntityNotFoundException.class);

        // When
        sut.saveArticleComment(articleCommentDto);

        // Then
        then(articleRepository).should().getReferenceById(articleCommentDto.getArticleId());
        then(userAccountRepository).shouldHaveNoInteractions();
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // Given
        String oldContent = "content";
        String updatedContent = "댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto articleCommentDto = createArticleCommentDto(updatedContent);

        given(articleCommentRepository.getReferenceById(articleCommentDto.getId())).willReturn(articleComment);

        // When
        sut.updateArticleComment(articleCommentDto);

        // Then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(articleCommentRepository).should().getReferenceById(articleCommentDto.getId());
    }

    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안한다.")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogWarningAndDoesNothing() {
        // Given
        ArticleCommentDto articleCommentDto = createArticleCommentDto("댓글");
        given(articleCommentRepository.getReferenceById(articleCommentDto.getId())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticleComment(articleCommentDto);

        // Then
        then(articleCommentRepository).should().getReferenceById(articleCommentDto.getId());
    }

    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // Given
        Long articleCommentId = 1L;
        String userId = "bhkim";
        willDoNothing().given(articleCommentRepository).deleteByIdAndUserAccount_UserId(articleCommentId, userId);

        // When
        sut.deleteArticleComment(articleCommentId, userId);

        // Then
        then(articleCommentRepository).should().deleteByIdAndUserAccount_UserId(articleCommentId, userId);
    }

    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "BHKIM",
                LocalDateTime.now(),
                "BHKIM"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "BHKIM",
                "password",
                "'bhkim@email.com",
                "BHKIM",
                "This is memo",
                LocalDateTime.now(),
                "BHKIM",
                LocalDateTime.now(),
                "BHKIM"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
          Article.of(createUserAccount(), "title", "content", "hashTag"),
          createUserAccount(),
          content
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "BHKIM",
                "password",
                "bhkim@eamil.com",
                "BHKIM",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }
}