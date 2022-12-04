package com.example.projectboard.api.AritlcleCommend.controller;

import com.example.projectboard.api.AritlcleCommend.payload.request.ArticleCommentRequest;
import com.example.projectboard.api.AritlcleCommend.service.ArticleCommentService;
import com.example.projectboard.api.User.payload.UserAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // TODO: 인증정보를 넣어줘야 하낟.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "bhkim", "pw", "bhkim@email.com", "bhkim", "memo"
        )));


        return "redirect:/articles/" + articleCommentRequest.getArticleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}
