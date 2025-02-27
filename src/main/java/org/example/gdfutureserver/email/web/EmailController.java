package org.example.gdfutureserver.email.web;


import lombok.extern.slf4j.Slf4j;
import org.example.gdfutureserver.email.model.Email;
import org.example.gdfutureserver.email.service.EmailCommandServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/gdfuture/server/api/email")
@Slf4j
public class EmailController {
    private final EmailCommandServiceImpl emailServiceCommand;

    public EmailController(EmailCommandServiceImpl emailServiceCommand) {
        this.emailServiceCommand = emailServiceCommand;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Email email) {
        emailServiceCommand.sendEmailForOffer(email);
        return ResponseEntity.ok("Email sent");
    }
}