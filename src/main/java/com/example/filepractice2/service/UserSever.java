package com.example.filepractice2.service;

import com.example.filepractice2.model.entity.User;
import com.example.filepractice2.model.request.UserLogin;
import com.example.filepractice2.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserSever implements IUserService {

    @Autowired
    private UserRepository userRepository;

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
}
