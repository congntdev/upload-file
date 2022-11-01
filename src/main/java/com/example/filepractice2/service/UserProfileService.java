package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.ProfileEntity;
import com.example.filepractice2.model.entity.UserEntity;
import com.example.filepractice2.model.request.ProfileRequest;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.response.ProfileResponse;
import com.example.filepractice2.repository.ProfileRepository;
import com.example.filepractice2.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserProfileService implements IUserService, IProfileService {

    @Autowired
    private ProfileRepository profileResponse;

    @Autowired
    private UserRepository userRepository;

    @Value("file")
    private String storageLocation;

    @Override
    public ProfileEntity getProfile(Long id) {
        return profileResponse.findProfileById(id).get();
    }

    @Override
    public List<ProfileEntity> getAllProfile() {
        return profileResponse.findAll();
    }

    @Override
    public ProfileResponse getProfileByUser(UserLogin login) {
        return userRepository.findProfileByUser(login.getUsername(), login.getPassword());
    }

    @Override
    public ProfileEntity creatProfile(Long id, ProfileRequest request) {
        Optional<UserEntity> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            log.error("user aren't match");
            return null;
        }
        ProfileEntity profile = new ProfileEntity();
        profile.setName(request.getName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setEmail(request.getEmail());
        profile.setUserId(id);
        profileResponse.save(profile);
        return profile;
    }

    @Override
    public ProfileEntity uploadFile(Long id, MultipartFile file) throws Exception {
        if (file == null || file.getOriginalFilename() == null) {
            throw new Exception("File is not null");
        }
        String fileName = StringUtils
                .cleanPath(Integer.valueOf(LocalDateTime.now().getNano()).toString())
                + ".png";
        try {
            ProfileEntity profile = profileResponse.findProfileById(id).orElseThrow(() ->
                    new Exception("Profile is not found with id: " + id)
            );
            if (fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path dir = Paths.get(storageLocation);
            Path targetLocation = dir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            ProfileEntity profileFile = profileResponse.getReferenceById(id);
            profileFile.setAvatarUrl(storageLocation + fileName);
            profileResponse.save(profileFile);
            return profile;
        } catch (IOException ex) {
            throw new Exception("Could not store file" + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFile(Long id) throws Exception {
        try {
            ProfileEntity profile = profileResponse.findProfileById(id).orElseThrow(() ->
                    new Exception("File is not found with id: " + id)
            );
            Path filePath = Paths.get(storageLocation).resolve(profile.getName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + profile.getName());
            }
        } catch (Exception e) {
            throw new Exception("Load file fail");
        }
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findUserById(id).get();
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity login(UserLogin request) {
        Optional<UserEntity> user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user.isEmpty()) {
            log.error("Username and password aren't match");
            return null;
        }
        return user.get();
    }

    @Override
    public UserEntity createUser(UserLogin request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return user;
    }
}
