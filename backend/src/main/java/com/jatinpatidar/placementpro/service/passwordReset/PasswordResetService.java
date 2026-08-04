package com.jatinpatidar.placementpro.service.passwordReset;

import com.jatinpatidar.placementpro.dto.auth.response.ForgotPasswordResponse;

public interface PasswordResetService {
      ForgotPasswordResponse forgotPassword(String email);
}
