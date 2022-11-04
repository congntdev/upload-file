package com.example.filepractice2.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserProfileResponse {
    private String username;
    private String password;
    private String name;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String email;
    private Long userId;
}
