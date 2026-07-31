package com.jatinpatidar.placementpro.service.user;

import com.jatinpatidar.placementpro.dto.auth.request.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.response.RegisterResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest request);
}
