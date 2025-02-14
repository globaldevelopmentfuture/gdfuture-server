package org.example.gdfutureserver.users.service;


import org.example.gdfutureserver.users.dtos.CreateUserRequest;
import org.example.gdfutureserver.users.dtos.UpdateUserRequest;
import org.example.gdfutureserver.users.dtos.UserResponse;

public interface UserCommandService {

    UserResponse createUser(CreateUserRequest createUserRequest);

    UserResponse deleteUser(long id);

    UserResponse updateUser(UpdateUserRequest updateUserRequest, long id);

}
