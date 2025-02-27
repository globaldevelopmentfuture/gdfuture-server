package org.example.gdfutureserver.email.service;

import org.example.gdfutureserver.email.model.Email;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailCommandServiceImpl implements EmailCommandService {

    private final JavaMailSender mailSender;

    public EmailCommandServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendEmailForOffer(Email emailData) {
        String subject = "New Offer Request";
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("globaldevelopmentfuture@gmail.com");
            helper.setTo("globaldevelopmentfuture@gmail.com");
            helper.setSubject(subject);

            String htmlContent =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "    <meta charset=\"UTF-8\">" +
                            "    <title>Offer Request</title>" +
                            "</head>" +
                            "<body style=\"margin:0; padding:0; background-color:#1F2937; font-family: Arial, sans-serif;\">" +
                            "    <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#1F2937; min-height:100vh;\">" +
                            "        <tr>" +
                            "            <td align=\"center\" style=\"padding:20px;\">" +
                            "                <!-- Container principal -->" +
                            "                <table role=\"presentation\" width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#2d3748; border-radius:8px; overflow:hidden; box-shadow:0 10px 15px rgba(0,0,0,0.3); margin:0 auto;\">" +
                            "                    <!-- Header -->" +
                            "                    <tr>" +
                            "                        <td style=\"background:#F59E0B; background:linear-gradient(90deg, #F59E0B, #FCD34D); padding:30px; text-align:center;\">" +
                            "                            <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
                            "                                <tr>" +
                            "                                    <td align=\"center\">" +
                            "                                        <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"display:inline-table;\">" +
                            "                                            <tr>" +
                            "                                                <td>" +
                            "                                                    <h1 style=\"margin:0; font-size:28px; font-weight:800; color:#1F2937; font-family:Arial, sans-serif;\">GDFUTURE</h1>" +
                            "                                                </td>" +
                            "                                            </tr>" +
                            "                                        </table>" +
                            "                                    </td>" +
                            "                                </tr>" +
                            "                            </table>" +
                            "                        </td>" +
                            "                    </tr>" +
                            "                    <!-- Conținut -->" +
                            "                    <tr>" +
                            "                        <td style=\"padding:40px 30px; background-color:#2d3748;\">" +
                            "                            <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
                            "                                <tr>" +
                            "                                    <td>" +
                            "                                        <h2 style=\"margin:0 0 20px; font-size:24px; font-weight:bold; color:#FCD34D; font-family:Arial, sans-serif;\">New Offer Request</h2>" +
                            "                                        <p style=\"margin:0 0 20px; font-size:16px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif;\">" +
                            "                                            Ai primit un mesaj nou de la  <strong>" + emailData.getName() + "</strong>." +
                            "                                        </p>" +
                            "                                        <p style=\"margin:0 0 20px; font-size:16px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif;\">" +
                            "                                            Iata detaliile:" +
                            "                                        </p>" +
                            "                                        <ul style=\"margin:0 0 20px; padding-left:20px; font-size:16px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif;\">" +
                            "                                            <li><strong>Name:</strong> " + safeValue(emailData.getName()) + "</li>" +
                            "                                            <li><strong>Company:</strong> " + safeValue(emailData.getCompany()) + "</li>" +
                            "                                            <li><strong>Email:</strong> " + safeValue(emailData.getEmail()) + "</li>" +
                            "                                            <li><strong>Phone:</strong> " + safeValue(emailData.getPhone()) + "</li>" +
                            "                                            <li><strong>Message:</strong> " + safeValue(emailData.getMessage()) + "</li>" +
                            "                                        </ul>" +
                            "                                        <p style=\"margin:0; font-size:16px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif;\">" +
                            "                                            Te rugam să iei legatura cu acest potential client cat mai curand posibil." +
                            "                                        </p>" +
                            "                                    </td>" +
                            "                                </tr>" +
                            "                            </table>" +
                            "                        </td>" +
                            "                    </tr>" +
                            "                    <!-- Footer -->" +
                            "                    <tr>" +
                            "                        <td style=\"background-color:#1F2937; padding:20px; text-align:center;\">" +
                            "                            <p style=\"margin:0; font-size:14px; color:#a0aec0; font-family:Arial, sans-serif;\">&copy; 2025 GDFUTURE. All rights reserved.</p>" +
                            "                        </td>" +
                            "                    </tr>" +
                            "                </table>" +
                            "            </td>" +
                            "        </tr>" +
                            "    </table>" +
                            "</body>" +
                            "</html>";

            helper.setText(htmlContent, true);
        };

        mailSender.send(preparator);
    }


    private String safeValue(String value) {
        return value == null ? "" : value;
    }


    @Override
    public void sendEmailResetPassword(String email, String resetLink) {
        String subject = "Password Reset";
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("globaldevelopmentfuture@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);
            String htmlContent = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <title>Password Reset</title>" +
                    "</head>" +
                    "<body style=\"margin:0; padding:0; background-color:#1F2937; font-family: Arial, sans-serif;\">" +
                    "    <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#1F2937; min-height:100vh;\">" +
                    "        <tr>" +
                    "            <td align=\"center\" style=\"padding:20px;\">" +
                    "                <!-- Content Container -->" +
                    "                <table role=\"presentation\" width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#2d3748; border-radius:8px; overflow:hidden; box-shadow:0 10px 15px rgba(0,0,0,0.3); margin:0 auto;\">" +
                    "                    <tr>" +
                    "                        <td style=\"background:#F59E0B; background:linear-gradient(90deg, #F59E0B, #FCD34D); padding:30px; text-align:center;\">" +
                    "                            <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
                    "                                <tr>" +
                    "                                    <td align=\"center\">" +
                    "                                        <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"display:inline-table;\">" +
                    "                                            <tr>" +

                    "                                                <td>" +
                    "                                                    <h1 style=\"margin:0; font-size:28px; font-weight:800; color:#1F2937; font-family:Arial, sans-serif;\">GDFUTURE</h1>" +
                    "                                                </td>" +
                    "                                            </tr>" +
                    "                                        </table>" +
                    "                                    </td>" +
                    "                                </tr>" +
                    "                            </table>" +
                    "                        </td>" +
                    "                    </tr>" +
                    "                    <tr>" +
                    "                        <td style=\"padding:40px 30px; background-color:#2d3748;\">" +
                    "                            <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
                    "                                <tr>" +
                    "                                    <td>" +
                    "                                        <h2 style=\"margin:0 0 20px; font-size:24px; font-weight:bold; color:#FCD34D; font-family:Arial, sans-serif;\">Password Reset Request</h2>" +
                    "                                        <p style=\"margin:0 0 20px; font-size:16px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif;\">We received a request to reset your password. If you did not request this, please ignore this email.</p>" +
                    "                                        <p style=\"margin:0 0 30px; font-size:16px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif;\">To reset your password, please click the button below:</p>" +
                    "                                    </td>" +
                    "                                </tr>" +
                    "                                <tr>" +
                    "                                    <td align=\"center\" style=\"padding:20px 0 30px;\">" +
                    "                                        <!-- Button -->" +
                    "                                        <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0 auto;\">" +
                    "                                            <tr>" +
                    "                                                <td align=\"center\" style=\"background:#F59E0B; background:linear-gradient(90deg, #F59E0B, #FCD34D); border-radius:6px;\">" +
                    "                                                    <a href=\"" + resetLink + "\" style=\"display:inline-block; padding:16px 24px; color:#1F2937; font-size:16px; font-weight:bold; text-decoration:none; font-family:Arial, sans-serif;\">Reset Password &rarr;</a>" +
                    "                                                </td>" +
                    "                                            </tr>" +
                    "                                        </table>" +
                    "                                    </td>" +
                    "                                </tr>" +
                    "                                <tr>" +
                    "                                    <td style=\"background-color:#4a5568; padding:20px; border-radius:6px;\">" +
                    "                                        <p style=\"margin:0 0 10px; font-size:14px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif;\">If you're having trouble clicking the button, copy and paste the URL below into your web browser:</p>" +
                    "                                        <p style=\"margin:0; font-size:14px; line-height:1.5; color:#e2e8f0; font-family:Arial, sans-serif; word-break:break-all;\">" + resetLink + "</p>" +
                    "                                    </td>" +
                    "                                </tr>" +
                    "                            </table>" +
                    "                        </td>" +
                    "                    </tr>" +
                    "                    <tr>" +
                    "                        <td style=\"background-color:#1F2937; padding:20px; text-align:center;\">" +
                    "                            <p style=\"margin:0; font-size:14px; color:#a0aec0; font-family:Arial, sans-serif;\">&copy; 2025 GDFUTURE. All rights reserved.</p>" +
                    "                        </td>" +
                    "                    </tr>" +
                    "                </table>" +
                    "            </td>" +
                    "        </tr>" +
                    "    </table>" +
                    "</body>" +
                    "</html>";
            helper.setText(htmlContent, true);
        };
        mailSender.send(preparator);
    }
}
