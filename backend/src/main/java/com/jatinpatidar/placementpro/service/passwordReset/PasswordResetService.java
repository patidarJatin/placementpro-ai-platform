package com.jatinpatidar.placementpro.service.passwordReset;

import com.jatinpatidar.placementpro.dto.auth.request.ResetPasswordRequest;
import com.jatinpatidar.placementpro.dto.auth.response.ForgotPasswordResponse;
import com.jatinpatidar.placementpro.dto.auth.response.ResetPasswordResponse;

public interface PasswordResetService {
      ForgotPasswordResponse forgotPassword(String email);
      ResetPasswordResponse resetPassword(ResetPasswordRequest request);
}
