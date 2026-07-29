package com.jatinpatidar.placementpro.service.impl;

import com.jatinpatidar.placementpro.dto.auth.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.RegisterResponse;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.enums.Role;
import com.jatinpatidar.placementpro.exceptions.EmailAlreadyExistsException;
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
    public RegisterResponse registerUser(RegisterRequest request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()){
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail((request.getEmail()));
        user.setPassword(request.getPassword());

        user.setRole(Role.STUDENT);

        User savedUser = userRepository.save(user);

        String message = "User registered successfully";
        RegisterResponse registerResponse =
                new RegisterResponse(savedUser.getId(), savedUser.getFullName(), savedUser.getEmail(),savedUser.getRole(),message);

        return registerResponse;
    }


}
