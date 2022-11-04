package com.example.filepractice2.controller;

import com.example.filepractice2.model.entity.Profile;
import com.example.filepractice2.model.request.ProfileRequest;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.response.ProfileResponse;
import com.example.filepractice2.service.IProfileService;
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
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private IProfileService service;

    @PostMapping("/{id}")
    ResponseEntity<Profile> createProfileByUser(@PathVariable Long id, @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(service.creatProfile(id, request));
    }

    @GetMapping
    ResponseEntity<List<Profile>> getAll() {
        return ResponseEntity.ok(service.getAllProfile());
    }

    @GetMapping("/{id}")
    ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProfile(id));
    }

    @PostMapping("/login")
    ResponseEntity<ProfileResponse> getProfileByUser(@RequestBody UserLogin login) {
        return ResponseEntity.ok(service.getProfileByUser(login));
    }

    @PutMapping("/image/{id}")
    ResponseEntity<String> upImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(service.uploadFile(file));
    }

    @GetMapping("/image/{id}")
    ResponseEntity<Resource> getImage(@PathVariable Long id, HttpServletRequest request) throws Exception {
        Resource resource = service.loadFile(id);
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
