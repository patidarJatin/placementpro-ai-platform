package com.jatinpatidar.placementpro.service.passwordReset;

import com.jatinpatidar.placementpro.dto.auth.response.ForgotPasswordResponse;
import com.jatinpatidar.placementpro.entity.PasswordResetToken;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.repository.PasswordResetTokenRepository;
import com.jatinpatidar.placementpro.repository.UserRepository;
import com.jatinpatidar.placementpro.service.email.EmailService;
import com.jatinpatidar.placementpro.service.token.TokenService;
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

    private static final int TOKEN_EXPIRY_MINUTES = 15;

    public PasswordResetServiceImpl(
            UserRepository userRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            TokenService tokenService,
            EmailService emailService) {

        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public ForgotPasswordResponse forgotPassword(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        // Production-ready response (don't reveal whether email exists)
        if (userOptional.isEmpty()) {
            return new ForgotPasswordResponse(
                    "If this email exists, a password reset link has been sent"
            );
        }

        User user = userOptional.get();

        // Generate new token and expiry
        String token = tokenService.generateToken();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);

        // Find existing token or create a new one
        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByUser(user)
                        .orElseGet(() -> {
                            PasswordResetToken resetToken = new PasswordResetToken();
                            resetToken.setUser(user);
                            return resetToken;
                        });

        // Update common fields
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryTime(expiryTime);

        // Save (insert or update)
        passwordResetTokenRepository.save(passwordResetToken);

        // Send email (currently prints to console)
        emailService.sendPasswordResetEmail(
                user.getEmail(),
                token
        );

        return new ForgotPasswordResponse(
                "If this email exists, a password reset link has been sent"
        );
    }
}
