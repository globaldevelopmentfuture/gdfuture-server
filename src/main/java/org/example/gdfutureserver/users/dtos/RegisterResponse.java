package org.example.gdfutureserver.users.dtos;


import org.example.gdfutureserver.system.security.UserRole;

public record RegisterResponse(String jwtToken,
                               String fullName,
                               String phone,
                               String email,
                               UserRole userRole) {
}
