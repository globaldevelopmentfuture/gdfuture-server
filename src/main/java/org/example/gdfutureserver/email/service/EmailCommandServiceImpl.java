package org.example.gdfutureserver.email.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailCommandServiceImpl implements EmailCommandService {

    private final  JavaMailSender mailSender;

    public EmailCommandServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendEmailResetPassword(String email,String resetLink) {
            String subject = "Password Reset";
            MimeMessagePreparator preparator = mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom("globaldevelopmentfuture@gmail.com");
                helper.setTo(email);
                helper.setSubject(subject);
                String htmlContent = "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<title>PathPilot</title>"
                        + "</head>"
                        + "<body>"
                        + "<div style=\"text-align: center;\">"
                        + "<h2>Password Reset</h2>"
                        + "<p>You've requested to reset your password.</p>"
                        + "<p>Please click the button below to reset your password:</p>"
                        + "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;\">Reset Password</a>"
                        + "</div>"
                        + "</body>"
                        + "</html>";

                helper.setText(htmlContent, true);
            };
            mailSender.send(preparator);
    }
}
