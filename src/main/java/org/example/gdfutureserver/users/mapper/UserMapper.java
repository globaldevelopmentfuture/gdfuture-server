package org.example.gdfutureserver.users.mapper;


import org.example.gdfutureserver.users.dtos.CreateUserRequest;
import org.example.gdfutureserver.users.dtos.UserResponse;
import org.example.gdfutureserver.users.model.User;

public class UserMapper {

    public static UserResponse userToResponseDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getPhone(),
                user.getUserRole());
    }

    public static User userRequestDtoToUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .email(createUserRequest.email())
                .fullName(createUserRequest.fullName())
                .password(createUserRequest.password())
                .phone(createUserRequest.phone()).build();
    }

}
