package com.jatinpatidar.placementpro.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendPasswordResetEmail(String userName, String toEmail, String token){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("PlacementPro - Password Recovery");

        message.setText(
                "Hello "+userName+",\n\n"+
                "Click the link to reset your password:\n\n"+
                        "http://localhost:5173/reset-password?token=" + token +"\n\n"+
                        "PlacementPro Team"
        );
        javaMailSender.send(message);
    }

}
