package com.jatinpatidar.placementpro.security.oauth;


import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.enums.AuthProvider;
import com.jatinpatidar.placementpro.enums.Role;
import com.jatinpatidar.placementpro.repository.UserRepository;
import com.jatinpatidar.placementpro.service.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public OAuth2LoginSuccessHandler(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
             Authentication authentication
    )throws IOException, ServletException {
        OAuth2User oauth2User =
                (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        String fullName = oauth2User.getAttribute("name");

        Optional<User> userOptional = userRepository.findByEmail(email);


        User user;
        if (userOptional.isEmpty()) {

            String randomPassword = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(randomPassword);

            user = new User();

            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(encodedPassword);
            user.setRole(Role.STUDENT);
            user.setProvider(AuthProvider.GOOGLE);

            user = userRepository.save(user);


        } else {
            user = userOptional.get();

        }

        String jwt = jwtService.generateToken(user);
        response.sendRedirect(
                frontendUrl+"/oauth/success?token=" + jwt
        );

    }
}
