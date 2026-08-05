package com.jatinpatidar.placementpro.service.passwordReset;

import com.jatinpatidar.placementpro.dto.auth.request.ResetPasswordRequest;
import com.jatinpatidar.placementpro.dto.auth.response.ForgotPasswordResponse;
import com.jatinpatidar.placementpro.dto.auth.response.ResetPasswordResponse;
import com.jatinpatidar.placementpro.entity.PasswordResetToken;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.exceptions.InvalidTokenException;
import com.jatinpatidar.placementpro.exceptions.PasswordMismatchException;
import com.jatinpatidar.placementpro.repository.PasswordResetTokenRepository;
import com.jatinpatidar.placementpro.repository.UserRepository;
import com.jatinpatidar.placementpro.service.email.EmailService;
import com.jatinpatidar.placementpro.service.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private static final int TOKEN_EXPIRY_MINUTES = 15;

    public PasswordResetServiceImpl(
            UserRepository userRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            TokenService tokenService,
            EmailService emailService,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public ForgotPasswordResponse forgotPassword(String email) {

        // Step 1: Check whether the user exists (always return a generic response for security)
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return new ForgotPasswordResponse(
                    "If this email exists, a password reset link has been sent"
            );
        }

        User user = userOptional.get();

        // Step 2: Generate a new reset token with an expiry time
        String token = tokenService.generateToken();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);

        // Step 3: Reuse an existing token or create a new one
        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByUser(user)
                        .orElseGet(() -> {
                            PasswordResetToken resetToken = new PasswordResetToken();
                            resetToken.setUser(user);
                            return resetToken;
                        });

        // Step 4: Update and save the latest token details
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryTime(expiryTime);

        passwordResetTokenRepository.save(passwordResetToken);

        // Step 5: Send the password reset email
        emailService.sendPasswordResetEmail(
                user.getEmail(),
                token
        );

        return new ForgotPasswordResponse(
                "If this email exists, a password reset link has been sent"
        );
    }

    @Transactional
    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {

        // Step 1: Validate the reset token
        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByToken(request.getToken())
                        .orElseThrow(() ->
                                new InvalidTokenException("Invalid or expired reset token"));

        // Step 2: Ensure the token has not expired
        if (passwordResetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Invalid or expired reset token");
        }

        // Step 3: Verify that both passwords match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException(
                    "New password and confirm password do not match");
        }

        // Step 4: Encode and update the user's password
        User user = passwordResetToken.getUser();

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        // Step 5: Remove the used reset token (one-time use)
        passwordResetTokenRepository.delete(passwordResetToken);

        return new ResetPasswordResponse("Password reset successfully");
    }
}
