package org.example.gdfutureserver.users.service;

import lombok.RequiredArgsConstructor;
import org.example.gdfutureserver.image.model.ImageFile;
import org.example.gdfutureserver.image.service.ImageCommandService;
import org.example.gdfutureserver.system.security.UserRole;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final ImageCommandService imageCommandService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request, MultipartFile avatarFile) throws Exception {
        User user = User.builder()
                .fullName(request.fullName())
                .phone(request.phone())
                .email(request.email())
                .userRole(UserRole.UTILIZATOR)
                .location(request.location())
                .experience(request.experience())
                .teamPosition(request.teamPosition())
                .skills(request.skills())
                .build();

        List<User> existingUsers = userRepository.findAll();
        existingUsers.forEach(u -> {
            if (user.getEmail().equalsIgnoreCase(u.getEmail())) {
                throw new UserAlreadyExists("User with this email already exists");
            }
        });

        user.setPassword(null);

        if (avatarFile != null && !avatarFile.isEmpty()) {
            ImageFile uploadedImage = imageCommandService.uploadImage(avatarFile);
            user.setAvatar(uploadedImage);
        }

        userRepository.saveAndFlush(user);
        return UserMapper.userToResponseDto(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request, MultipartFile avatarFile) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoUserFound("No user with id = " + id + " found"));

        List<User> allUsers = userRepository.findAll();
        allUsers.remove(user);
        allUsers.forEach(u -> {
            if (request.email().equalsIgnoreCase(u.getEmail())) {
                throw new UserAlreadyExists("User with this email already exists");
            }
        });

        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setEmail(request.email());
        user.setLocation(request.location());
        user.setExperience(request.experience());
        user.setTeamPosition(request.teamPosition());
        user.setSkills(request.skills());

        if (user.getUserRole() == UserRole.ADMIN && request.password() != null && !request.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        } else if (user.getUserRole() == UserRole.UTILIZATOR) {
            user.setPassword(null);
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            if (user.getAvatar() != null) {
                ImageFile updatedImage = imageCommandService.updateImage(user.getAvatar(), avatarFile);
                user.setAvatar(updatedImage);
            } else {
                ImageFile newImage = imageCommandService.uploadImage(avatarFile);
                user.setAvatar(newImage);
            }
        }

        userRepository.save(user);
        return UserMapper.userToResponseDto(user);
    }

    @Override
    public UserResponse deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoUserFound("No user with id = " + id + " found"));

        if (user.getUserRole() == UserRole.ADMIN) {
            throw new RuntimeException("Cannot delete the only admin user");
        }

        if (user.getAvatar() != null) {
            imageCommandService.deleteImage(user.getAvatar());
        }

        UserResponse response = UserMapper.userToResponseDto(user);
        userRepository.delete(user);
        return response;
    }
}
