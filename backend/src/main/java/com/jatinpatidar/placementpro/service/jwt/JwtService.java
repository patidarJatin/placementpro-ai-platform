package com.jatinpatidar.placementpro.service.jwt;

import com.jatinpatidar.placementpro.entity.User;

public interface JwtService {

    String generateToken(User user);

    String extractEmail(String token);

    boolean isTokenValid(String token,User user);
}
