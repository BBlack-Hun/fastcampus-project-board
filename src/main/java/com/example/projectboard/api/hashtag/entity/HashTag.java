package com.example.projectboard.api.hashtag.entity;

import com.example.projectboard.api.Article.entity.Article;
import com.example.projectboard.api.Common.entity.AuditingFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(
        name = "article_hashTag",
        indexes = {
                @Index(columnList = "hashTagName", unique = true),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        }
)
@Entity
public class HashTag extends AuditingFields {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ToString.Exclude
        @ManyToMany(mappedBy = "hashTags")
        private Set<Article> articles = new LinkedHashSet<>();

        @Setter
        @Column(nullable = false)
        private String hashTagName; // 해시태그 이름

        protected HashTag() {}

        private HashTag(String hashTagName) {
                this.hashTagName = hashTagName;
        }

        public static HashTag of(String hashTagName) {
                return new HashTag(hashTagName);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof HashTag)) return false;
                HashTag that = (HashTag) o;
                return this.getId() != null && this.getId().equals(that.getId());
        }

        @Override
        public int hashCode() {
                return Objects.hash(this.getId());
        }
}
