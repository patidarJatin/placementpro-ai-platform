package com.jatinpatidar.placementpro.controller;


import com.jatinpatidar.placementpro.dto.auth.request.ForgotPasswordRequest;
import com.jatinpatidar.placementpro.dto.auth.request.LoginRequest;
import com.jatinpatidar.placementpro.dto.auth.request.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.response.ForgotPasswordResponse;
import com.jatinpatidar.placementpro.dto.auth.response.LoginResponse;
import com.jatinpatidar.placementpro.dto.auth.response.RegisterResponse;
import com.jatinpatidar.placementpro.service.auth.AuthService;
import com.jatinpatidar.placementpro.service.passwordReset.PasswordResetService;
import com.jatinpatidar.placementpro.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    public AuthController(AuthService authService, UserService userService,
                          PasswordResetService passwordResetService) {
        this.authService = authService;
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.loginResponse(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgetPassword(@Valid @RequestBody ForgotPasswordRequest request){
        ForgotPasswordResponse forgotPasswordResponse = passwordResetService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(forgotPasswordResponse);
    }

    @GetMapping("/google/login")
    public void googleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }
}
