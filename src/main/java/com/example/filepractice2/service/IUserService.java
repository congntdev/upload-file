package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.User;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.request.UserRequest;
import com.example.filepractice2.model.response.ProfileResponse;
import com.example.filepractice2.model.response.UserProfileResponse;

import java.util.List;

public interface IUserService {

    User getUser(Long id);

    List<User> getAllUser();

    User login(UserLogin request);

    User createUser(UserLogin request);

    UserProfileResponse create(UserRequest request) throws Exception;
}
