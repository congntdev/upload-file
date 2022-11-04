package com.example.filepractice2.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserRequest implements Serializable {
    @NotBlank(message = "username is not blank")
    private String username;
    private String password;
    private String name;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String email;
    private Long userId;
    private MultipartFile avatar;
}
