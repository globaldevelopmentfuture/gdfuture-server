package org.example.gdfutureserver.users.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(@NotNull String fullName,
                                @NotNull String email,
                                @NotNull String password,
                                @NotNull String phone) {
}
