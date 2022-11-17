package com.example.projectboard.api.AritlcleCommend.repository;

import com.example.projectboard.api.AritlcleCommend.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
