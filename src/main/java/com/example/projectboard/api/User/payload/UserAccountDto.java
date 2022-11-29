package com.example.projectboard.api.User.payload;

import com.example.projectboard.api.User.entity.UserAccount;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.example.projectboard.api.User.entity.UserAccount} entity
 */
@Data
public class UserAccountDto {

    private final String userId;
    private final String userPassword;
    private final String email;
    private final String nickname;
    private final String memo;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static UserAccountDto of(String userId, String userPassword, String email, String nickname, String memo,
                                    LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, email, nickname, memo, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo
        );
    }
}