package com.jatinpatidar.placementpro.service.passwordReset;

import com.jatinpatidar.placementpro.dto.auth.response.ForgotPasswordResponse;
import com.jatinpatidar.placementpro.entity.PasswordResetToken;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.repository.PasswordResetTokenRepository;
import com.jatinpatidar.placementpro.repository.UserRepository;
import com.jatinpatidar.placementpro.service.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService{

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final TokenService tokenService;
    private static final int TOKEN_EXPIRY_MINUTES = 15;

    public PasswordResetServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository, TokenService tokenService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    @Override
    public ForgotPasswordResponse forgotPassword(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            return new ForgotPasswordResponse(
                    "If this email exists, a password reset link has been sent"
            );
        }
        User user = userOptional.get();

        Optional<PasswordResetToken>  existingToken = passwordResetTokenRepository.findByUser(user);

        existingToken.ifPresent(passwordResetTokenRepository::delete);

        String token = tokenService.generateToken();

        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);

        PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setUser(user);

        passwordResetToken.setToken(token);

        passwordResetToken.setExpiryTime(expiryTime);

        passwordResetTokenRepository.save(passwordResetToken);

        // TODO: Send email

        return new ForgotPasswordResponse(
                "If this email exists, a password reset link has been sent"
        );
    }
}
