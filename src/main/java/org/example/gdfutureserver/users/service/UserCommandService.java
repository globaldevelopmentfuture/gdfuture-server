package org.example.gdfutureserver.users.service;

import org.example.gdfutureserver.users.dtos.CreateUserRequest;
import org.example.gdfutureserver.users.dtos.UpdateUserRequest;
import org.example.gdfutureserver.users.dtos.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserCommandService {
    UserResponse createUser(CreateUserRequest request, MultipartFile avatarFile) throws Exception;
    UserResponse updateUser(Long id, UpdateUserRequest request, MultipartFile avatarFile) throws Exception;
    UserResponse deleteUser(Long id);
}