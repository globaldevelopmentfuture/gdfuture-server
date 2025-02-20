package org.example.gdfutureserver.password.service;

public interface PasswordResetTokenCommandService {

    void resetPasswordRequest(String email);

    void resetPassword(String token, String password);
}
