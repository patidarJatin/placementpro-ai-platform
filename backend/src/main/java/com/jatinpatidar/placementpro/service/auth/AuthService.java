package com.jatinpatidar.placementpro.service.auth;

import com.jatinpatidar.placementpro.dto.auth.request.LoginRequest;
import com.jatinpatidar.placementpro.dto.auth.response.LoginResponse;

public interface AuthService  {
    LoginResponse loginResponse(LoginRequest request);
}
