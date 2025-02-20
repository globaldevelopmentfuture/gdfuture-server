package org.example.gdfutureserver.email.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface EmailCommandService {

    void sendEmailResetPassword(String email,String resetLink);

}
