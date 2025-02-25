package org.example.gdfutureserver.users.mapper;

import org.example.gdfutureserver.users.dtos.UserResponse;
import org.example.gdfutureserver.users.model.User;

public class UserMapper {
    public static UserResponse userToResponseDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getPhone(),
                user.getDescription(),
                user.getEmail(),
                user.getUserRole(),
                user.getLocation(),
                user.getGithub(),
                user.getLinkedin(),
                user.getAvatar() != null ? user.getAvatar().getUrl() : null,
                user.getTeamPosition(),
                user.getSkills(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
