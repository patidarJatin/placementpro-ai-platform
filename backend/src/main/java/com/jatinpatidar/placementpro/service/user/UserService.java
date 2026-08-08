package com.jatinpatidar.placementpro.service.user;

import com.jatinpatidar.placementpro.dto.auth.request.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.response.RegisterResponse;
import com.jatinpatidar.placementpro.dto.profile.response.UserProfileResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest request);
    UserProfileResponse getCurrentUserProfile();
}
