package com.example.projectboard.api.Article.repository;

import com.example.projectboard.api.AritlcleCommend.repository.ArticleCommentRepository;
import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.User.entity.UserAccount;
import com.example.projectboard.api.User.repository.UserAccountRepository;
import com.example.projectboard.api.hashtag.entity.HashTag;
import com.example.projectboard.api.hashtag.repository.HashTagRepository;
import com.example.projectboard.core.util.config.JpaConfig;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    private final HashTagRepository hashTagRepository;

    JpaRepositoryTest(
        @Autowired ArticleRepository articleRepository,
        @Autowired ArticleCommentRepository articleCommentRepository,
        @Autowired UserAccountRepository userAccountRepository,
        @Autowired HashTagRepository hashTagRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
        this.hashTagRepository = hashTagRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
            .isNotNull()
            .hasSize(123); // classpath:resources/data.sql 참조
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(
            UserAccount.of("newBHKIM", "pw", null, null, null));
        Article article = Article.of(userAccount, "new Article", "new Content");

        article.addHashTags(Stream.of(HashTag.of("spring")).collect(Collectors.toSet()));

        // When
        articleRepository.save(article);

        // Then
        assertThat(articleRepository.count())
            .isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("id 값이 존재하지 않습니다."));
        HashTag updatedHashtag = HashTag.of("springboot");
        article.clearHashTags();
        article.addHashTags(Stream.of(updatedHashtag).collect(Collectors.toSet()));

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle.getHashTags())
            .hasSize(1)
            .extracting("hashTagName", String.class)
            .containsExactly(updatedHashtag.getHashTagName());
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("id 값이 존재하지 않습니다."));
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count())
            .isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count())
            .isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }

    @DisplayName("[Querydsl] 전체 hashtag 리스트에서 이름만 조회하기")
    @Test
    void givenNothing_whenQueryingHashTags_thenReturnsHashTagNames() {
        // Given

        // When
        List<String> hashTagNames = hashTagRepository.findAllHashTagNames();

        // Then
        assertThat(hashTagNames).hasSize(19);
    }

    @DisplayName("[Querydsl] hashtag로 페이징된 게시글 검색하기")
    @Test
    void givenHashTagNamesAndPageable_whenQueryingArticles_thenReturnsArticlePage() {
        // Given
        List<String> hashtagNames = Arrays.asList("blue", "crimson", "fuscia");
        Pageable pageable = PageRequest.of(0, 5, Sort.by(
            Sort.Order.desc("hashTags.hashTagName"),
            Sort.Order.asc("title")
        ));

        // When
        Page<Article> articlePage = articleRepository.findByHashTagNames(hashtagNames, pageable);

        // Then
        assertThat(articlePage.getContent()).hasSize(pageable.getPageSize());
        assertThat(articlePage.getContent().get(0).getTitle()).isEqualTo("Fusce posuere felis sed lacus.");
        assertThat(articlePage.getContent().get(0).getHashTags())
            .extracting("hashTagName", String.class)
            .containsExactly("fuscia");
        assertThat(articlePage.getTotalElements()).isEqualTo(17);
        assertThat(articlePage.getTotalPages()).isEqualTo(4);
    }

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {

        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("bhkim");
        }
    }
}
