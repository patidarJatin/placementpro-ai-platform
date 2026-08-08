package com.jatinpatidar.placementpro.controller;

import com.jatinpatidar.placementpro.dto.profile.response.UserProfileResponse;
import com.jatinpatidar.placementpro.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(){
        UserProfileResponse userProfileResponse = userService.getCurrentUserProfile();
        return ResponseEntity.ok(userProfileResponse);
    }
}
