package com.example.projectboard.api.Article.entity;


import com.example.projectboard.api.AritlcleCommend.entity.ArticleComment;
import com.example.projectboard.api.Common.entity.AuditingFields;
import com.example.projectboard.api.User.entity.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "article",
        indexes = {
                @Index(columnList = "title"),
                @Index(columnList = "hashTag"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy"),
        }
)
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter
    @Column(nullable = false)
    private String title; // 제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용
    @Setter
    private String hashTag; // 해시태그

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    private  Article(UserAccount userAccount, String title, String content, String hashTag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashTag = hashTag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashTag) {
        return new Article(userAccount, title, content, hashTag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id); // 새로만든 객체에 대해서는 다 다른 값으로 정의한다.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
