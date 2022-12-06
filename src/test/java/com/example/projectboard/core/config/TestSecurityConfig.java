package com.example.projectboard.core.config;

import com.example.projectboard.api.User.entity.UserAccount;
import com.example.projectboard.api.User.repository.UserAccountRepository;
import com.example.projectboard.core.util.config.SecurityConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void SecuritySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "bhkimTest",
                "pw",
                "bhkim_test@email.com",
                "bhkim-test",
                "bhkim-test"
        )));
    }
}
