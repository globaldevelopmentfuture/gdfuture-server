package org.example.gdfutureserver.password.service;

import org.example.gdfutureserver.password.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenQueryServiceImpl implements PasswordResetTokenQueryService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenQueryServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    @Override
    public boolean isTokenValidOrExpired(String token) {
        return passwordResetTokenRepository.isTokenValidOrExpired(token);
    }
}
