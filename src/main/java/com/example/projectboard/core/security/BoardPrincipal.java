package com.example.projectboard.core.security;

import com.example.projectboard.api.User.payload.UserAccountDto;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class BoardPrincipal implements UserDetails {
    private final String username;
    private final String password;
    Collection<? extends GrantedAuthority> authorities;
    private final String email;
    private final String nickname;
    private final String memo;


    public static BoardPrincipal of(String username, String password, String email, String nickname, String memo) {
        Set<RoleType> roleTypes = new HashSet<>(Collections.singletonList(RoleType.User));
        return new BoardPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet()).toString(),
                nickname,
                memo
        );
    }

    public  static BoardPrincipal from(UserAccountDto userAccountDto) {
        return BoardPrincipal.of(
                userAccountDto.getUserId(),
                userAccountDto.getUserPassword(),
                userAccountDto.getEmail(),
                userAccountDto.getNickname(),
                userAccountDto.getMemo()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum RoleType {
        User("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}


