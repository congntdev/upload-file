package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.ProfileEntity;
import com.example.filepractice2.model.request.ProfileRequest;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.response.ProfileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProfileService {

    ProfileEntity getProfile(Long id);

    List<ProfileEntity> getAllProfile();

    ProfileResponse getProfileByUser(UserLogin login);

    ProfileEntity creatProfile(Long id, ProfileRequest request);

    ProfileEntity uploadFile(Long id, MultipartFile file) throws Exception;

    Resource loadFile(Long id) throws Exception;

}
