package org.example.gdfutureserver.users.service;

import lombok.AllArgsConstructor;

import org.example.gdfutureserver.users.dtos.UserResponse;
import org.example.gdfutureserver.users.dtos.UserResponseList;
import org.example.gdfutureserver.users.exceptions.NoUserFound;
import org.example.gdfutureserver.users.mapper.UserMapper;
import org.example.gdfutureserver.users.model.User;
import org.example.gdfutureserver.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private UserRepository userRepository;


    @Override
    public UserResponseList getAllUsers() {
        List<User> list = userRepository.findAll();
        if (list.isEmpty()) {
            throw new NoUserFound("No users found");
        }
        List<UserResponse> responses = new ArrayList<>();
        list.forEach(user -> {
            responses.add(UserMapper.userToResponseDto(user));
        });
        return new UserResponseList(responses);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoUserFound("No user with this email found"));
    }
}
