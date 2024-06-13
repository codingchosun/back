package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.ExistLoginId;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.RegisterUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final DataJpaUserRepository dataJpaUserRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(RegisterUserRequest registerUserRequest) {

        User IdCheck = dataJpaUserRepository.findByLoginId(registerUserRequest.getLoginId());

        if(IdCheck != null) {
            throw new ExistLoginId("이미 존재하는 id 입니다.", HttpStatus.NOT_ACCEPTABLE);
        }

        String encodedPassword = passwordEncoder.encode(registerUserRequest.getPassword());
        registerUserRequest.setPassword(encodedPassword);
        User user = new User(registerUserRequest);
        return dataJpaUserRepository.save(user);
    }
}
