package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.UserRepository;
import com.codingchosun.backend.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.getLoginId());
        if (user != null && user.getPassword().equals(request.getPassword())) {return user;}
        return null;
    }

}