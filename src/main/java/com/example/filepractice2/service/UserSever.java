package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.Profile;
import com.example.filepractice2.model.entity.User;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.model.request.UserRequest;
import com.example.filepractice2.model.response.UserProfileResponse;
import com.example.filepractice2.repository.ProfileRepository;
import com.example.filepractice2.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserSever implements IUserService {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private IProfileService profileService;

    @Override
    public User getUser(Long id) {
        return userRepository.findUserById(id).get();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User login(UserLogin request) {
        Optional<User> user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user.isEmpty()) {
            log.error("Username and password aren't match");
            return null;
        }
        return user.get();
    }

    @Override
    public User createUser(UserLogin request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return user;
    }

    @Override
    public UserProfileResponse create(UserRequest request) throws Exception {
        User user = new User()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword());
        userRepository.save(user);

        Profile profile = new Profile().setEmail(request.getEmail())
                .setName(request.getName());
        profile.setUserId(user.getId());
        String avatarUrl = profileService.uploadFile(request.getAvatar());
        profile.setAvatarUrl(avatarUrl);
        profileRepository.save(profile);


        UserProfileResponse response = new UserProfileResponse()
                .setUserId(profile.getUserId())
                .setAvatarUrl(profile.getAvatarUrl())
                .setEmail(profile.getEmail())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword());
        return response;
    }
}
