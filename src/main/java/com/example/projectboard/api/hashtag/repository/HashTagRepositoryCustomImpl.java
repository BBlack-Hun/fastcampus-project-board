package com.example.projectboard.api.hashtag.repository;

import com.example.projectboard.api.hashtag.entity.HashTag;
import com.example.projectboard.api.hashtag.entity.QHashTag;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * HashTagRepositoryCustomImpl class.
 *
 * <p>
 * date: 2023-03-04
 * </p>
 *
 * @author : 김정훈 파트장
 * @version : V1.0.0
 */
public class HashTagRepositoryCustomImpl extends QuerydslRepositorySupport implements
    HashTagRepositoryCustom {

    public HashTagRepositoryCustomImpl() {
        super(HashTag.class);
    }

    @Override
    public List<String> findAllHashTagNames() {
        QHashTag hashtag = QHashTag.hashTag;

        return from(hashtag)
            .select(hashtag.hashTagName)
            .fetch();
    }
}
