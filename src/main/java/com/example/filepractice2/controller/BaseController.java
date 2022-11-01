package com.example.filepractice2.controller;

import com.example.filepractice2.model.entity.ProfileEntity;
import com.example.filepractice2.model.entity.UserEntity;
import com.example.filepractice2.model.request.ProfileRequest;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.response.ProfileResponse;
import com.example.filepractice2.service.IProfileService;
import com.example.filepractice2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IProfileService profileService;

    @PostMapping("/user")
    ResponseEntity<UserEntity> createUser(@RequestBody UserLogin request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/user")
    ResponseEntity<List<UserEntity>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/user/{id}")
    ResponseEntity<ProfileEntity> createProfileByUser(@PathVariable Long id, @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(profileService.creatProfile(id, request));
    }

    @GetMapping("/profile")
    ResponseEntity<List<ProfileEntity>> getAllProfile() {
        return ResponseEntity.ok(profileService.getAllProfile());
    }

    @GetMapping("/profile/{id}")
    ResponseEntity<ProfileEntity> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfile(id));
    }

    @PostMapping("/login")
    ResponseEntity<ProfileResponse> LoginProfileByUser(@RequestBody UserLogin login) {
        return ResponseEntity.ok(profileService.getProfileByUser(login));
    }

    @PutMapping("/profile/{id}")
    ResponseEntity<ProfileEntity> upImage(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws Exception {
        return ResponseEntity.ok(profileService.uploadFile(id, file));
    }

    @GetMapping("/profile/{id}")
    ResponseEntity<Resource> getFile(@PathVariable Long id, HttpServletRequest request) throws Exception {
        Resource resource = profileService.loadFile(id);
        String contentType = null;
        try {
            contentType = request.getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ignored) {

        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + resource.getFilename() + "\"")
                .body(resource);
    }
}
