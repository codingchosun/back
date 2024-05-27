package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    DataJpaUserRepository dataJpaUserRepository;

    public LoginService(DataJpaUserRepository dataJpaUserRepository) {
        this.dataJpaUserRepository = dataJpaUserRepository;
    }

    public User login(LoginRequest request) {
        User user = dataJpaUserRepository.findByLoginId(request.getLoginId());
        if (user != null && user.getPassword().equals(request.getPassword())) {return user;}
        return null;
    }

}