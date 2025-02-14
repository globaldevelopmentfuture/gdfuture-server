package org.example.gdfutureserver.users.dtos;


import org.example.gdfutureserver.system.security.UserRole;

public record UserResponse(long id, String email, String password, String fullName, String phone, UserRole userRole) {
}
