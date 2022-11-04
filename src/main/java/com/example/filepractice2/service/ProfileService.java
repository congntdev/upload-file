package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.Profile;
import com.example.filepractice2.model.entity.User;
import com.example.filepractice2.model.request.ProfileRequest;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.response.ProfileResponse;
import com.example.filepractice2.repository.ProfileRepository;
import com.example.filepractice2.repository.UserRepository;
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
public class ProfileService implements IProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("file")
    private String storageLocation;

    @Override
    public Profile getProfile(Long id) {
        return profileRepository.findProfileById(id).get();
    }

    @Override
    public List<Profile> getAllProfile() {
        return profileRepository.findAll();
    }

    @Override
    public ProfileResponse getProfileByUser(UserLogin login) {
        return userRepository.findProfileByUser(login.getUsername(), login.getPassword());
    }


    @Override
    public Profile creatProfile(Long id, ProfileRequest request) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            log.error("user aren't match");
            return null;
        }
        Profile profile = new Profile();
        profile.setName(request.getName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setEmail(request.getEmail());
        profile.setUserId(id);
        profileRepository.save(profile);
        return profile;
    }

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        if (file == null || file.getOriginalFilename() == null) {
            throw new Exception("File is not null");
        }
        String fileName = StringUtils
                .cleanPath(Integer.valueOf(LocalDateTime.now().getNano()).toString())
                + ".png";
        try {
            if (fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path dir = Paths.get(storageLocation);
            Path targetLocation = dir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return storageLocation + "/" + fileName;
        } catch (IOException ex) {
            throw new Exception("Could not store file" + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFile(Long id) throws Exception {
        try {
            Profile profile = profileRepository.findProfileById(id).orElseThrow(() ->
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
    public Profile create(ProfileRequest request) {
        Profile profile = new Profile();
        profile.setName(request.getName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setEmail(request.getEmail());
        profile.setUserId(request.getUserId());
        profileRepository.save(profile);
        return profile;
    }
}
