package com.jatinpatidar.placementpro.service.user;

import com.jatinpatidar.placementpro.dto.auth.request.RegisterRequest;
import com.jatinpatidar.placementpro.dto.auth.response.RegisterResponse;
import com.jatinpatidar.placementpro.dto.profile.response.UserProfileResponse;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.enums.AuthProvider;
import com.jatinpatidar.placementpro.enums.Role;
import com.jatinpatidar.placementpro.exceptions.EmailAlreadyExistsException;
import com.jatinpatidar.placementpro.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        user.setRole(Role.STUDENT);

        User savedUser = userRepository.save(user);

        String message = "User registered successfully";
        RegisterResponse registerResponse =
                new RegisterResponse(savedUser.getId(), savedUser.getFullName(), savedUser.getEmail(),savedUser.getRole(),message);

        return registerResponse;
    }

    @Override
    public UserProfileResponse getCurrentUserProfile(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       User user = (User) authentication.getPrincipal();
       user.getId();
       user.getFullName();

       UserProfileResponse userProfileResponse = new UserProfileResponse(
               user.getId(), user.getFullName()
       );
       return userProfileResponse;
    }

}
