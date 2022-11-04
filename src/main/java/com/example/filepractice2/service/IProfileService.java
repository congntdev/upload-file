package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.Profile;
import com.example.filepractice2.model.request.ProfileRequest;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.response.ProfileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProfileService {

    Profile getProfile(Long id);

    List<Profile> getAllProfile();

    ProfileResponse getProfileByUser(UserLogin login);

    Profile creatProfile(Long id, ProfileRequest request);

    String uploadFile(MultipartFile file) throws Exception;

    Resource loadFile(Long id) throws Exception;

    Profile create(ProfileRequest request);


}
