package org.example.gdfutureserver.password.repository;

import org.example.gdfutureserver.password.model.PasswordResetToken;
import org.example.gdfutureserver.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PasswordResetToken p WHERE p.token = ?1 AND p.expiryDate > CURRENT_TIMESTAMP")
    boolean isTokenValidOrExpired(String token);

}
