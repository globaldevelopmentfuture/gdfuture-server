package org.example.gdfutureserver.password.service;

public interface PasswordResetTokenQueryService {

        boolean isTokenValidOrExpired(String token);
}
