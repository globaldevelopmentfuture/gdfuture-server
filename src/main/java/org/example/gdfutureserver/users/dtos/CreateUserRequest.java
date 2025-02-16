package org.example.gdfutureserver.users.dtos;

import jakarta.validation.constraints.NotNull;
import org.example.gdfutureserver.system.security.UserRole;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "Full name is required") String fullName,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Password is required") String password,
        @NotBlank(message = "Phone is required") String phone,
        @NotNull(message = "User role is required") UserRole userRole
) {}

