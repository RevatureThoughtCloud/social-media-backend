package com.revature.repositories;

import com.revature.models.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, String> {
    Optional<PasswordToken> findByUserEmail(String email);

    Optional<PasswordToken> findByPasswordToken(String passwordToken);
}