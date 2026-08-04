package com.jatinpatidar.placementpro.service.email;

public interface EmailService {
    void sendPasswordResetEmail(String email, String token);
}
