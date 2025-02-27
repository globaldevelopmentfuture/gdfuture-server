package org.example.gdfutureserver.email.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gdfutureserver.email.model.Email;
import org.springframework.stereotype.Service;


public interface EmailCommandService {

    void sendEmailResetPassword(String email,String resetLink);

    void sendEmailForOffer(Email email);

}
