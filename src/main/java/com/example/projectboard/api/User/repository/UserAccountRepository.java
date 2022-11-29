package com.example.projectboard.api.User.repository;

import com.example.projectboard.api.User.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
