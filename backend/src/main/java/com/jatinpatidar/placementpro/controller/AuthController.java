package com.jatinpatidar.placementpro.controller;


import com.jatinpatidar.placementpro.dto.auth.request.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.response.RegisterResponse;
import com.jatinpatidar.placementpro.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

}
