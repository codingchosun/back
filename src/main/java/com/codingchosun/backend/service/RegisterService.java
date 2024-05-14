package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.RegisterUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService {

    private final DataJpaUserRepository dataJpaUserRepository;

    public User signUp(RegisterUserRequest registerUserRequest) {
        User user = new User(registerUserRequest);
        return dataJpaUserRepository.save(user);
    }
}
