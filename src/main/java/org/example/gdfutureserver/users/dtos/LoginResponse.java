package org.example.gdfutureserver.users.dtos;


import org.example.gdfutureserver.system.security.UserRole;

public record LoginResponse(String jwtToken,
                            Long id,
                            String fullName,
                            String phone,
                            String email,
                            UserRole userRole) {
}
