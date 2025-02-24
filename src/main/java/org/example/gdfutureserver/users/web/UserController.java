package org.example.gdfutureserver.users.web;

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
                loginUser.getEmail(),
                loginUser.getUserRole(),
                loginUser.getLocation(),
                loginUser.getExperience(),
                Optional.ofNullable(loginUser.getAvatar()).map(ImageFile::getUrl).orElse(null),
                loginUser.getTeamPosition(),
                loginUser.getSkills() == null ? Set.of() : loginUser.getSkills()
        );

        return new ResponseEntity<>(loginResponse, jwtHeader, HttpStatus.OK);
    }


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> createUser(
            @RequestPart("user") CreateUserRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile
    )  {
        try {
            UserResponse response = userCommandService.createUser(request, avatarFile);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
           e.printStackTrace();
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestPart("user") UpdateUserRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile
    ) throws Exception {
        UserResponse response = userCommandService.updateUser(id, request, avatarFile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        UserResponse response = userCommandService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
