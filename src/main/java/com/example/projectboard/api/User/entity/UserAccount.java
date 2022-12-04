package com.example.projectboard.api.User.entity;

import com.example.projectboard.api.Common.entity.AuditingFields;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "user_account",
        indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class UserAccount extends AuditingFields {

    @Id
    @Column(length = 50)
    private String userId;

    @Setter
    @Column(nullable = false)
    private String userPassword;

    @Setter
    @Column(length = 100)
    private String email;

    @Setter
    @Column(length = 100)
    private String nickname;

    @Setter
    private String memo;

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        UserAccount that = (UserAccount) o;
        return userId != null && userId.equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
