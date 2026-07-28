package com.jatinpatidar.placementpro.controller;


import com.jatinpatidar.placementpro.dto.auth.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.RegisterResponse;
import com.jatinpatidar.placementpro.service.UserService;
import jakarta.validation.Valid;
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
    public RegisterResponse registerUser(@Valid @RequestBody RegisterRequest registerRequest){
           return userService.registerUser(registerRequest);
    }

}
