package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.UserEntity;
import com.example.filepractice2.model.request.ProfileRequest;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.response.ProfileResponse;

import java.util.List;

public interface IUserService {

    UserEntity getUser(Long id);

    List<UserEntity> getAllUser();

    UserEntity login(UserLogin request);

    UserEntity createUser(UserLogin request);
}
