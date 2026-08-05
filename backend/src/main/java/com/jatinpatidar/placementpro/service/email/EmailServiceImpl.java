package com.jatinpatidar.placementpro.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;

    public EmailServiceImpl(JavaMailSender javaMailSender, ResourceLoader resourceLoader) {
        this.javaMailSender = javaMailSender;
        this.resourceLoader = resourceLoader;
    }

    private String loadEmailTemplate(String userName, String resetLink) {

        try {

            Resource resource =
                    resourceLoader.getResource(
                            "classpath:templates/password-reset-email.html"
                    );

            String html =
                    new String(resource.getInputStream().readAllBytes());

            html = html.replace("{{name}}", userName);
            html = html.replace("{{resetLink}}", resetLink);

            return html;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load email template");
        }
    }

    @Override
    public void sendPasswordResetEmail(
            String userName,
            String toEmail,
            String token
    ) {

        try {

            String resetLink =
                    frontendUrl + "/reset-password?token=" + token;


            String htmlContent =
                    loadEmailTemplate(userName, resetLink);


            MimeMessage message =
                    javaMailSender.createMimeMessage();


            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");


            helper.setTo(toEmail);
            helper.setSubject("PlacementPro - Password Recovery");

            helper.setText(
                    htmlContent,
                    true
            );


            javaMailSender.send(message);


        } catch (MessagingException e) {

            throw new RuntimeException(
                    "Failed to send password reset email"
            );
        }
    }

}
