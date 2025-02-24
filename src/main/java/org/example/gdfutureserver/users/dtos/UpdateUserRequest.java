package org.example.gdfutureserver.users.dtos;

import org.example.gdfutureserver.users.enums.TeamPosition;

import java.util.Set;

public record UpdateUserRequest(
        String fullName,
        String phone,
        String email,
        String password,
        String location,
        String experience,
        TeamPosition teamPosition,
        Set<String> skills
) {
}
