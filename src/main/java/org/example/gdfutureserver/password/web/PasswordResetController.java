package org.example.gdfutureserver.password.web;

import org.example.gdfutureserver.password.dto.PasswordResetDTO;
import org.example.gdfutureserver.password.model.PasswordResetToken;
import org.example.gdfutureserver.password.service.PasswordResetTokenCommandServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/gdfuture/server/api/password")
public class PasswordResetController {

    private final PasswordResetTokenCommandServiceImpl  passwordResetService;

    public PasswordResetController(PasswordResetTokenCommandServiceImpl passwordResetService) {
        this.passwordResetService = passwordResetService;
    }
    @PostMapping("/password-reset-request/{email}")
    public ResponseEntity<?> requestReset(@PathVariable String email) {
        passwordResetService.resetPasswordRequest(email);
        return ResponseEntity.ok("Dacă există un cont asociat acestei adrese, vei primi un email de resetare.");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDTO resetDTO) {
        passwordResetService.resetPassword(resetDTO.token(), resetDTO.newPassword());
        return ResponseEntity.ok("Parola a fost resetată cu succes.");
    }
}
