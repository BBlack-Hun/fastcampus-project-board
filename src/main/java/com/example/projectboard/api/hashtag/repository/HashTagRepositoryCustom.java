package com.example.projectboard.api.hashtag.repository;

import java.util.List;

/**
 * HashTagRepositoryCustom class.
 *
 * <p>
 * date: 2023-03-04
 * </p>
 *
 * @author : 김정훈 파트장
 * @version : V1.0.0
 */
public interface HashTagRepositoryCustom {

    List<String> findAllHashTagNames();
}
