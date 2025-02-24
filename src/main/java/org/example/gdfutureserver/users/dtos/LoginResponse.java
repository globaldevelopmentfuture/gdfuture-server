package org.example.gdfutureserver.users.dtos;

import org.example.gdfutureserver.system.security.UserRole;
import org.example.gdfutureserver.users.enums.TeamPosition;

import javax.annotation.Nullable;
import java.util.Set;

public record LoginResponse(
        String jwtToken,
        Long id,
        String fullName,
        String phone,
        String email,
        UserRole userRole,
        @Nullable String location,
        @Nullable String experience,
        @Nullable String avatar,
        @Nullable TeamPosition teamPosition,
        @Nullable Set<String> skills
) {
}
