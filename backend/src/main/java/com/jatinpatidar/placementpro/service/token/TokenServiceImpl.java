package com.jatinpatidar.placementpro.service.token;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
