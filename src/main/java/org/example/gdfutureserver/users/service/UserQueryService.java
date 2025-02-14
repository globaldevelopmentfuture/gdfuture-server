package org.example.gdfutureserver.users.service;


import org.example.gdfutureserver.users.dtos.UserResponse;
import org.example.gdfutureserver.users.dtos.UserResponseList;
import org.example.gdfutureserver.users.model.User;

public interface UserQueryService {

    UserResponse findUserById(long id);

    UserResponseList getAllUsers();

    User findByEmail(String email);

}
