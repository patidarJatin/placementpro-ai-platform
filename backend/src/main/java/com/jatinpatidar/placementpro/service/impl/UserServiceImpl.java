package com.jatinpatidar.placementpro.service.impl;

import com.jatinpatidar.placementpro.dto.RegisterRequest;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.repository.UserRepository;
import com.jatinpatidar.placementpro.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(RegisterRequest request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()){
            throw new RuntimeException("Email already registered");
        }
    }


}
