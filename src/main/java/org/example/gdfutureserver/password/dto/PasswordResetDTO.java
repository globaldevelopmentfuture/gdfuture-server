package org.example.gdfutureserver.password.dto;

public record PasswordResetDTO(String token, String newPassword) {
}
