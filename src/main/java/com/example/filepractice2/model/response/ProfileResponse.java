package com.example.filepractice2.model.response;

import lombok.Data;

import java.time.LocalDate;

public interface ProfileResponse {

    Long getId();
    LocalDate getDateOfBirth();
    String getAvatarUrl();
    String getEmail();
    Long getUserId();
}
