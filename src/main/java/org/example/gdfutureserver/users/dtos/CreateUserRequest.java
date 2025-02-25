package org.example.gdfutureserver.users.dtos;

import org.example.gdfutureserver.system.security.UserRole;
import org.example.gdfutureserver.users.enums.TeamPosition;

import java.util.Set;

public record CreateUserRequest(
        String fullName,
        String phone,

        String description,
        String email,
        String password,
        String location,

        String github,

        String linkedin,

        TeamPosition teamPosition,
        Set<String> skills
) {
}
