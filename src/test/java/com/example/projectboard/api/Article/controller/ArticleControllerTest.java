package com.example.projectboard.api.Article.controller;

import com.example.projectboard.api.Article.entity.type.SearchType;
import com.example.projectboard.api.Article.service.ArticleService;
import com.example.projectboard.api.Common.payload.ArticleWithCommentsDto;
import com.example.projectboard.api.Common.service.PaginationService;
import com.example.projectboard.api.User.payload.UserAccountDto;
import com.example.projectboard.util.config.SecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;
    @MockBean
    private PaginationService paginationService;

    ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {

        // Given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(Arrays.asList(0, 1, 2, 3, 4));

        // When & Then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 검색어와 함께 호출")
    @Test
    public void givenSearchKeyword_whenSearchingArticleView_thenReturnsArticlesView() throws Exception {

        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        given(articleService.searchArticles(eq(searchType), eq(searchKeyword), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(Arrays.asList(0, 1, 2, 3, 4));

        // When & Then
        mockMvc.perform(
                        get("/articles")
                                .queryParam("searchType", searchType.name())
                                .queryParam("searchKeyword", searchKeyword)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attributeExists("searchTypes"));
        then(articleService).should().searchArticles(eq(searchType), eq(searchKeyword), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }


    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenWSearchingArticlesView_thenReturnsArticleView() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = Arrays.asList(1, 2, 3, 4, 5);
        given(articleService.searchArticles(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mockMvc.perform(
                        get("/articles")
                                .queryParam("page", String.valueOf(pageNumber))
                                .queryParam("size", String.valueOf(pageSize))
                                .queryParam("sort", sortName + "," + direction)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(articleService).should().searchArticles(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search"));
    }

    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchHashTagView_thenReturnsArticleSearchHashTagView() throws Exception {

        // Given
        List<String> hashTags = Arrays.asList("#java", "#spring", "#boot");
        given(articleService.searchArticlesViaHashTag(eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(articleService.getHashTags()).willReturn(hashTags);
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(Arrays.asList(1,2,3,4,5));

        // When & Then
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashTag"))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attribute("hashTags", hashTags))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attribute("searchType", SearchType.HASHTAG));
        then(articleService).should().searchArticlesViaHashTag(eq(null), any(Pageable.class));
        then(articleService).should().getHashTags();
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());


    }

    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출, 해시태그 입력")
    @Test
    public void givenHashTag_whenRequestingArticleSearchHashTagView_thenReturnsArticleSearchHashTagView() throws Exception {

        // Given
        String HashTag = "#java";
        List<String> hashTags = Arrays.asList("#java", "#spring", "#boot");
        given(articleService.searchArticlesViaHashTag(eq(HashTag), any(Pageable.class))).willReturn(Page.empty());
        given(articleService.getHashTags()).willReturn(hashTags);
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(Arrays.asList(1,2,3,4,5));

        // When & Then
        mockMvc.perform(
                get("/articles/search-hashtag")
                        .queryParam("searchKeyword", HashTag)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashTag"))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attribute("hashTags", hashTags))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attribute("searchType", SearchType.HASHTAG));
        then(articleService).should().searchArticlesViaHashTag(eq(HashTag), any(Pageable.class));
        given(articleService.getHashTags()).willReturn(hashTags);
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(Arrays.asList(1,2,3,4,5));

    }


    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {

        // Given
        Long articleId = 1L;
        Long totalCount = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
        given(articleService.getArticleCount()).willReturn(totalCount);

        // When & Then
        mockMvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"))
                .andExpect(model().attribute("totalCount", totalCount));
        then(articleService).should().getArticle(articleId);
        then(articleService).should().getArticleCount();
    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                new HashSet<>(Collections.emptyList()),
                "title",
                "content",
                "#java",
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
                "bhkim@email.com",
                "Bhkim",
                "memo",
                LocalDateTime.now(),
                "bhkim",
                LocalDateTime.now(),
                "bhkim"
        );
    }

}
