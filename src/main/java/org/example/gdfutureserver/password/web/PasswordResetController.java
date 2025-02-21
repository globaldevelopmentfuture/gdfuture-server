package org.example.gdfutureserver.password.web;

import org.example.gdfutureserver.password.dto.PasswordResetDTO;
import org.example.gdfutureserver.password.model.PasswordResetToken;
import org.example.gdfutureserver.password.service.PasswordResetTokenCommandServiceImpl;
import org.example.gdfutureserver.password.service.PasswordResetTokenQueryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/gdfuture/server/api/password")
public class PasswordResetController {

    private final PasswordResetTokenCommandServiceImpl  passwordResetService;
    private final PasswordResetTokenQueryServiceImpl passwordResetTokenQueryService;

    public PasswordResetController(PasswordResetTokenCommandServiceImpl passwordResetService, PasswordResetTokenQueryServiceImpl passwordResetTokenQueryService) {
        this.passwordResetService = passwordResetService;
        this.passwordResetTokenQueryService = passwordResetTokenQueryService;
    }

    @GetMapping("/is-token-valid/{token}")
    public ResponseEntity<Boolean> isTokenValid(@PathVariable String token) {
        return ResponseEntity.ok(passwordResetTokenQueryService.isTokenValidOrExpired(token));
    }
    @PostMapping("/password-reset-request/{email}")
    public ResponseEntity<String> requestReset(@PathVariable String email) {
        passwordResetService.resetPasswordRequest(email);
        return ResponseEntity.ok("Daca exista un cont asociat acestei adrese, vei primi un email de resetare.");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO resetDTO) {
        passwordResetService.resetPassword(resetDTO.token(), resetDTO.newPassword());
        return ResponseEntity.ok("Parola a fost resetata cu succes.");
    }
}
