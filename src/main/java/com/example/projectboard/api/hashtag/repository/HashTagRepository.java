package com.example.projectboard.api.hashtag.repository;


import com.example.projectboard.api.hashtag.entity.HashTag;
import com.example.projectboard.api.hashtag.payload.HashTagDto;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HashTagRepository extends
    JpaRepository<HashTag, Long>,
    HashTagRepositoryCustom,
    QuerydslPredicateExecutor<HashTag> {

    Optional<HashTag> findByHashtagName(String hashtagName);

    List<HashTag> findByHashtagNameIn(Set<String> hashtagNames);
}
