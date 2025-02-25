package org.example.gdfutureserver.users.dtos;

import org.example.gdfutureserver.users.enums.TeamPosition;

import java.util.Set;

public record UpdateUserRequest(
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
