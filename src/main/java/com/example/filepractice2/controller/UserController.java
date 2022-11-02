package com.example.filepractice2.controller;

import com.example.filepractice2.model.entity.User;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
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
}
