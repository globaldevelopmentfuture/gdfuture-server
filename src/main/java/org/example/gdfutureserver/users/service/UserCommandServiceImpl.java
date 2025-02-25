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
import org.example.gdfutureserver.users.exceptions.UserNoAcces;
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

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExists("User with this email already exists");
        }

        User user = User.builder()
                .fullName(request.fullName())
                .phone(request.phone())
                .email(request.email())
                .userRole(UserRole.UTILIZATOR)
                .location(request.location())
                .teamPosition(request.teamPosition())
                .password(passwordEncoder.encode(request.password()))
                .skills(request.skills())
                .build();

        if (avatarFile != null && !avatarFile.isEmpty()) {
            ImageFile uploadedImage = imageCommandService.uploadImage(avatarFile);
            user.setAvatar(uploadedImage);
        }

        userRepository.saveAndFlush(user);
        return UserMapper.userToResponseDto(user);
    }

    @Override
    public UserResponse updateUser(String email, UpdateUserRequest request, MultipartFile avatarFile) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoUserFound("No user found with email: " + email));

        if (!user.getEmail().equalsIgnoreCase(request.email()) &&
                userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExists("Another user with this email already exists");
        }

        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setDescription(request.description());
        user.setEmail(request.email());
        user.setLocation(request.location());
        user.setGithub(request.github());
        user.setLinkedin(request.linkedin());
        user.setTeamPosition(request.teamPosition());
        user.setSkills(request.skills());

        if (request.password() != null && !request.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            ImageFile updatedImage = (user.getAvatar() != null) ?
                    imageCommandService.updateImage(user.getAvatar(), avatarFile) :
                    imageCommandService.uploadImage(avatarFile);
            user.setAvatar(updatedImage);
        }

        userRepository.save(user);

        return UserMapper.userToResponseDto(user);
    }


    @Override
    public String  deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoUserFound("No user found with email: " + email));
        if (user.getUserRole() == UserRole.ADMIN) {
            throw new UserNoAcces("Admin cannot be deleted.");
        }
        if (user.getAvatar() != null) {
            imageCommandService.deleteImage(user.getAvatar());
        }
        userRepository.delete(user);

        return "User with email : "+email+" was deleted";
    }
}
