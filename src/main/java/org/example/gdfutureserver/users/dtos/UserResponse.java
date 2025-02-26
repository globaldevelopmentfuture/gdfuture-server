package org.example.gdfutureserver.users.dtos;

import org.example.gdfutureserver.system.security.UserRole;
import org.example.gdfutureserver.users.enums.TeamPosition;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        long id,
        String fullName,
        String phone,

        String description,
        String email,
        UserRole userRole,
        String location,

        String github,

        String linkedin,

        String avatar,
        TeamPosition teamPosition,
        Set<String> skills ,
        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
