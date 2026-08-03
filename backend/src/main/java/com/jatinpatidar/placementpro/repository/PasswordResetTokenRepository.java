package com.jatinpatidar.placementpro.repository;

import com.jatinpatidar.placementpro.entity.PasswordResetToken;
import com.jatinpatidar.placementpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByUser(User user);

    Optional<PasswordResetToken> findByToken(String token);


}
