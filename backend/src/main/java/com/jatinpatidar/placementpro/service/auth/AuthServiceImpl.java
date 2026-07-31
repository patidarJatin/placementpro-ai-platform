package com.jatinpatidar.placementpro.service.auth;

import com.jatinpatidar.placementpro.dto.auth.request.LoginRequest;
import com.jatinpatidar.placementpro.dto.auth.response.LoginResponse;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.exceptions.InvalidCredentialsException;
import com.jatinpatidar.placementpro.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse loginResponse(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password")
                );


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return new LoginResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
