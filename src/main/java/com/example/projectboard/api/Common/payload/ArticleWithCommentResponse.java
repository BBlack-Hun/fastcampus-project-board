package com.example.projectboard.api.Common.payload;

import com.example.projectboard.api.Article.payload.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleWithCommentResponse {
    private List<ArticleWithCommentDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
