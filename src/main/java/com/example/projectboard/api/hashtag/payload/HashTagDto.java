package com.example.projectboard.api.hashtag.payload;

import com.example.projectboard.api.hashtag.entity.HashTag;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HashTagDto {

    private final Long id;
    private final String hashTagName;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static HashTagDto of(String hashTagName) {
        return new HashTagDto(null, hashTagName, null, null, null, null);
    }

    public static HashTagDto of(Long id, String hashTagName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new HashTagDto(id, hashTagName, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static HashTagDto from(HashTag entity) {
        return new HashTagDto(
                entity.getId(),
                entity.getHashTagName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public HashTag toEntity() {
        return HashTag.of(hashTagName);
    }


}
