package org.example.gdfutureserver.users.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gdfutureserver.image.model.ImageFile;
import org.example.gdfutureserver.system.jwt.JWTTokenProvider;
import org.example.gdfutureserver.system.security.UserRole;
import org.example.gdfutureserver.users.dtos.*;
import org.example.gdfutureserver.users.exceptions.UserNoAcces;
import org.example.gdfutureserver.users.model.User;
import org.example.gdfutureserver.users.service.UserCommandService;
import org.example.gdfutureserver.users.service.UserQueryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

import static org.example.gdfutureserver.system.constants.Constants.JWT_TOKEN_HEADER;

@RestController
@AllArgsConstructor
@RequestMapping("/gdfuture/server/api/user")
@CrossOrigin
@Slf4j
public class UserController {

    private UserCommandService userCommandService;
    private UserQueryService userQueryService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user) {
        User loginUser = userQueryService.findByEmail(user.email());
        authenticate(user.email(), user.password());

        if (loginUser.getUserRole() != UserRole.ADMIN) {
            throw new UserNoAcces("User has no access to login");
        }

        HttpHeaders jwtHeader = getJwtHeader(loginUser);

        LoginResponse loginResponse = new LoginResponse(
                jwtHeader.getFirst(JWT_TOKEN_HEADER),
                loginUser.getId(),
                loginUser.getFullName(),
                loginUser.getPhone(),
                loginUser.getDescription(),
                loginUser.getEmail(),
                loginUser.getUserRole(),
                loginUser.getLocation(),
                loginUser.getGithub(),
                loginUser.getLinkedin(),
                Optional.ofNullable(loginUser.getAvatar()).map(ImageFile::getUrl).orElse(null),
                loginUser.getTeamPosition(),
                loginUser.getSkills() == null ? Set.of() : loginUser.getSkills()
        );

        return new ResponseEntity<>(loginResponse, jwtHeader, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> createUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile
    ) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserRequest request = objectMapper.readValue(userJson, CreateUserRequest.class);
        UserResponse response = userCommandService.createUser(request, avatarFile);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String email,
            @RequestPart("user") String userJson,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile
    ) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateUserRequest request = objectMapper.readValue(userJson, UpdateUserRequest.class);
        UserResponse response = userCommandService.updateUser(email, request, avatarFile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        String response = userCommandService.deleteUser(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<UserResponseList> getAllUsers() {
        UserResponseList users = userQueryService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(user));
        return headers;
    }
}
