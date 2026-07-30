package com.jatinpatidar.placementpro.dto.auth.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "FullName  is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message ="Enter a valid email" )
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,max = 20,message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = ".*[A-Z].*",
            message = "Password must contain at least one uppercase letter"
    )
    @Pattern(
            regexp = ".*[a-z].*",
            message = "Password must contain at least one lowercase letter"
    )
    @Pattern(
            regexp = ".*\\d.*",
            message = "Password must contain at least one number"
    )
    @Pattern(
            regexp = ".*[@#$%^&+=!].*",
            message = "Password must contain at least one special character"
    )
    private String password;
}

