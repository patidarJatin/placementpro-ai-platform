package com.jatinpatidar.placementpro.service;

import com.jatinpatidar.placementpro.dto.auth.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.RegisterResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest request);
}
