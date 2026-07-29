package com.jatinpatidar.placementpro.dto.auth.response;

import com.jatinpatidar.placementpro.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private String message;

}
