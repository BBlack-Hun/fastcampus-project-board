package com.example.projectboard.api.Article.service;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Article.entity.constant.SearchType;
import com.example.projectboard.api.Article.payload.ArticleDto;
import com.example.projectboard.api.Article.repository.ArticleRepository;
import com.example.projectboard.api.Common.payload.ArticleWithCommentsDto;
import com.example.projectboard.api.User.entity.UserAccount;
import com.example.projectboard.api.User.payload.UserAccountDto;
import com.example.projectboard.api.User.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @Mock private UserAccountRepository userAccountRepository;


    @DisplayName("검색어 없이 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {

        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable); // 제목, 본문, id, 닉네임, 해시태그

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {

        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable); // 제목, 본문, id, 닉네임, 해시태그

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }

    @DisplayName("검색없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticlesViaHashTag_thenReturnsEmptyPage() {

        // Given
        Pageable pageable = Pageable.ofSize(20);

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashTag(null, pageable);

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).shouldHaveNoInteractions();
    }

    @DisplayName("게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    void givenHashTag_whenSearchingArticlesViaHashTag_thenReturnsArticlesPage() {

        // Given
        String hashTag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashTag(hashTag, pageable)).willReturn(Page.empty(pageable));

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashTag(hashTag, pageable);

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashTag(hashTag, pageable);
    }


    @DisplayName("게시글 ID로 조회하면, 댓글 달린 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleWithComments_thenReturnsArticleWithComments() {
        // Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // When
        ArticleWithCommentsDto articleWithCommentsDto = sut.getArticleWithComments(articleId);

        // Then
        assertThat(articleWithCommentsDto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashTag", article.getHashTag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 달린 게시글이 없으면, 예외를 던진다.")
    @Test
    void givenNonexistentArticleId_whenSearchingArticleWithComment_thenThrowsException() {
        // Given
        Long articleId = 1L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(()-> sut.getArticleWithComments(articleId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다. - articleId: " + articleId);
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {

        // Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // When
        ArticleDto articleDto = sut.getArticle(articleId);

        // Then
        assertThat(articleDto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashTag", article.getHashTag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글이 없으면, 예외를 던진다.")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() {

        // Given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(()-> sut.getArticle(articleId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다. - articleId: " + articleId);
        then(articleRepository).should().findById(articleId);
    }



    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다. ")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {

        // Given
        ArticleDto articleDto = createArticleDto();
        given(userAccountRepository.getReferenceById(articleDto.getUserAccountDto().getUserId())).willReturn(createUserAccount());
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());

        // When
        sut.saveArticle(articleDto);

        // Then
        then(userAccountRepository).should().getReferenceById(articleDto.getUserAccountDto().getUserId());
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다. ")
    @Test
    void givenModifiedArticleInfo_whenUpdatingArticle_thenUpdatesArticle() {

        // Given
        Article article = createArticle();
        ArticleDto articleDto = createArticleDto("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(articleDto.getId())).willReturn(article);

        // When
        sut.updateArticle(articleDto.getId(), articleDto);

        // Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", articleDto.getTitle())
                        .hasFieldOrPropertyWithValue("content", articleDto.getContent())
                                .hasFieldOrPropertyWithValue("hashTag", articleDto.getHashTag());
        then(articleRepository).should().getReferenceById(articleDto.getId());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
    @Test
    void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() {
        // Given
        ArticleDto articleDto = createArticleDto("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(articleDto.getId())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticle(articleDto.getId(), articleDto);

        // Then
        then(articleRepository).should().getReferenceById(articleDto.getId());
    }



    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다. ")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {

        // Given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).deleteById(articleId);

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().deleteById(articleId);
    }

    @DisplayName("게시글 수를 조회하면, 게시글 수를 반환한다.")
    @Test
    void givenNothing_whenCountingArticles_thenReturnsArticlesCount() {
        // Given
        long expected  = 0L;
        given(articleRepository.count()).willReturn(expected);

        // When
        long actual = sut.getArticleCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(articleRepository).should().count();
    }

    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다.")
    @Test
    void givenNothing_whenCalling_thenReturnsHashTags() {
        // Given
        List<String> expectedHashTags = Arrays.asList("#java", "#spring", "#boot");
        given(articleRepository.findAllDistinctHashTags()).willReturn(expectedHashTags);

        // When
        List<String> actualHashTags = sut.getHashTags();

        // Then
        assertThat(actualHashTags).isEqualTo(expectedHashTags);
        then(articleRepository).should().findAllDistinctHashTags();
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "BHKIM",
                "password",
                "BHKIM@email.com",
                "BHKIM",
                null
        );
    }

    private Article createArticle() {
        Article article = Article.of(
                createUserAccount(),
                "title",
                "content",
                "#Java"
        );
        ReflectionTestUtils.setField(article, "id", 1L);

        return article;
    }

    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content", "#java");
    }

    private ArticleDto createArticleDto(String title, String content, String hashTag) {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                title,
                content,
                hashTag,
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
                "BHKIM@email.com",
                "BHKIM",
                "This is memo",
                LocalDateTime.now(),
                "BHKIM",
                LocalDateTime.now(),
                "BHKIM"
        );
    }

}