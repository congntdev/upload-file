package com.example.filepractice2.controller;

import com.example.filepractice2.model.entity.User;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.request.UserRequest;
import com.example.filepractice2.model.response.UserProfileResponse;
import com.example.filepractice2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService service;

    @PostMapping
    ResponseEntity<User> create(@RequestBody UserLogin request) {
        return ResponseEntity.ok(service.createUser(request));
    }

    @GetMapping
    ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAllUser());
    }

    @PostMapping("/profiles")
    ResponseEntity<UserProfileResponse> create(@ModelAttribute @Valid UserRequest request) throws Exception {
        return ResponseEntity.ok(service.create(request));
    }
}
