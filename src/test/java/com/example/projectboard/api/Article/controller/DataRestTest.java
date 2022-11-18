package com.example.projectboard.api.Article.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Spring Data REST 통합테스트는 불필요하므로 제외시킴")
@DisplayName("Data REST - API 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mockMvc;

    public DataRestTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNoting_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(get("/api/v1/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNoting_whenRequestingArticle_thenReturnsArticleJsonResponse() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(get("/api/v1/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }

    @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
    @Test
    void givenNoting_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(get("/api/v1/articles/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }

    @DisplayName("[api] 댓글 리스트 조회")
    @Test
    void givenNoting_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(get("/api/v1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void givenNoting_whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() throws Exception {

        // Given

        // When & Then
        mockMvc.perform(get("/api/v1/articleComments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }
}
