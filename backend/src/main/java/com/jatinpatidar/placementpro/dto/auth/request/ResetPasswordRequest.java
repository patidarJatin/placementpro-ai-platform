package com.jatinpatidar.placementpro.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Reset token is required")
    private String token;

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
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

}
