package com.jatinpatidar.placementpro.dto.auth.response;

import com.jatinpatidar.placementpro.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id;

    private String fullName;

    private String email;

    private Role role;

    private String token;

}
