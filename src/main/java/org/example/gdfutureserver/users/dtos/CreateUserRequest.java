package org.example.gdfutureserver.users.dtos;

import org.example.gdfutureserver.system.security.UserRole;
import org.example.gdfutureserver.users.enums.TeamPosition;

import java.util.Set;

public record CreateUserRequest(
        String fullName,
        String phone,
        String email,
        String password,
        UserRole userRole,
        String location,
        String experience,
        TeamPosition teamPosition,
        Set<String> skills
) {
}
