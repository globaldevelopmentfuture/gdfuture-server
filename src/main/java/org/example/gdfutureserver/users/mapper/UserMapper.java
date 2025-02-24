package org.example.gdfutureserver.users.mapper;

import org.example.gdfutureserver.users.dtos.UserResponse;
import org.example.gdfutureserver.users.model.User;

public class UserMapper {
    public static UserResponse userToResponseDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getPhone(),
                user.getEmail(),
                user.getUserRole(),
                user.getLocation(),
                user.getExperience(),
                user.getAvatar().getUrl(),
                user.getTeamPosition(),
                user.getSkills()
        );
    }
}

