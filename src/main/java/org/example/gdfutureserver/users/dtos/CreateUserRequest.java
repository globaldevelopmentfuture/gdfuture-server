package org.example.gdfutureserver.users.dtos;

import jakarta.validation.constraints.NotNull;
import org.example.gdfutureserver.system.security.UserRole;

public record CreateUserRequest(@NotNull String fullName,
                                @NotNull String email,
                                @NotNull String password,
                                @NotNull String phone,
                                @NotNull UserRole userRole) {
}
