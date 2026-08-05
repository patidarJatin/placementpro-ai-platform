package com.jatinpatidar.placementpro.service.email;

public interface EmailService {
    void sendPasswordResetEmail(String userName,String toEmail, String token);
}
