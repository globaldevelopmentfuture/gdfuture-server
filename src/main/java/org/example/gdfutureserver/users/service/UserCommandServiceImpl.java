package org.example.gdfutureserver.users.service;


import lombok.AllArgsConstructor;

import org.example.gdfutureserver.users.dtos.CreateUserRequest;
import org.example.gdfutureserver.users.dtos.UpdateUserRequest;
import org.example.gdfutureserver.users.dtos.UserResponse;
import org.example.gdfutureserver.users.exceptions.NoUserFound;
import org.example.gdfutureserver.users.exceptions.UserAlreadyExists;
import org.example.gdfutureserver.users.mapper.UserMapper;
import org.example.gdfutureserver.users.model.User;
import org.example.gdfutureserver.users.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserCommandServiceImpl implements UserCommandService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user  = User.builder()
                .phone(createUserRequest.phone())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .fullName(createUserRequest.fullName())
                .email(createUserRequest.email())
                .userRole(createUserRequest.userRole())
                .build();

        List<User> list = userRepository.findAll();

        list.forEach( user1 -> {
            if(user.getEmail().equals(user1.getEmail())){
                throw new UserAlreadyExists("User with this email already exists");
            }
        });

        userRepository.saveAndFlush(user);

        return UserMapper.userToResponseDto(user);
    }

    @Override
    public UserResponse deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoUserFound("No user with this id found"));


        UserResponse response = UserMapper.userToResponseDto(user);

        userRepository.delete(user);

        return response;
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest up, long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoUserFound("No user with this id found"));

        List<User> list = userRepository.findAll();
        list.remove(user);

        list.forEach( user1 -> {
            if(up.email().equals(user1.getEmail())){
                throw new UserAlreadyExists("User with this email already exists, please enter a different email address");
            }
        });
        user.setEmail(up.email());
        user.setFullName(up.fullName());
        user.setPassword(up.password());
        user.setPhone(up.phone());

        userRepository.save(user);

        return UserMapper.userToResponseDto(user);
    }
}
