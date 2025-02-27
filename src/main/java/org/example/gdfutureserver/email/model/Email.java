package org.example.gdfutureserver.email.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Email {
    private String name;
    private String company;
    private String email;
    private String phone;
    private String message;
}
