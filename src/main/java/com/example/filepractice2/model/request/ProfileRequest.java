package com.example.filepractice2.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileRequest {

    private String name;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String email;
    private Long userId;
}
