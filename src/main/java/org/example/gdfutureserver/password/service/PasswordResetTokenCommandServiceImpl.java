package org.example.gdfutureserver.password.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gdfutureserver.email.service.EmailCommandService;
import org.example.gdfutureserver.password.exception.TokenExpiredException;
import org.example.gdfutureserver.password.exception.TokenNotFoundException;
import org.example.gdfutureserver.password.model.PasswordResetToken;
import org.example.gdfutureserver.password.repository.PasswordResetTokenRepository;
import org.example.gdfutureserver.users.model.User;
import org.example.gdfutureserver.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.example.gdfutureserver.users.exceptions.NoUserFound;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class PasswordResetTokenCommandServiceImpl implements PasswordResetTokenCommandService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final EmailCommandService emailCommandService;
    @Value("${app.base.url}")
    private String baseUrl;

    @Override
    public void resetPasswordRequest(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoUserFound("User not found");
        }

        User foundUser = user.get();
        Optional<PasswordResetToken> existingTokenOpt = passwordResetTokenRepository.findByUser(foundUser);
        String tokenGenerated = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10);

        if (existingTokenOpt.isPresent()) {
            PasswordResetToken existingToken = existingTokenOpt.get();
            existingToken.setToken(tokenGenerated);
            existingToken.setExpiryDate(expiryDate);
            passwordResetTokenRepository.save(existingToken);
        } else {
            PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                    .token(tokenGenerated)
                    .user(foundUser)
                    .expiryDate(expiryDate)
                    .build();
            passwordResetTokenRepository.save(passwordResetToken);
        }

        String resetLink = baseUrl + "/password-reset?token=" + tokenGenerated;
        emailCommandService.sendEmailResetPassword(email, resetLink);
    }


    @Override
    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken.isEmpty()) {
            throw new TokenNotFoundException("Token not found");
        }
        if (passwordResetToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }
        User user = passwordResetToken.get().getUser();
        user.setPassword(newPassword);
        userRepository.saveAndFlush(user);
        passwordResetTokenRepository.delete(passwordResetToken.get());
    }
}
