package com.jatinpatidar.placementpro.service.passwordReset;

import com.jatinpatidar.placementpro.dto.auth.response.ForgotPasswordResponse;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.repository.PasswordResetTokenRepository;
import com.jatinpatidar.placementpro.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService{
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ForgotPasswordResponse forgotPasswordResponse(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            return new ForgotPasswordResponse(
                    "If this email exists, a password reset link has been sent"
            );
        }
        User user = userOptional.get();
        passwordResetTokenRepository.findByUser(user);

        // TODO: Generate token
        // TODO: Save token
        // TODO: Send email

        return new ForgotPasswordResponse(
                "If this email exists, a password reset link has been sent"
        );
    }
}
