package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.LoginRequest;
import com.codingchosun.backend.web.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LoginService {
    DataJpaUserRepository dataJpaUserRepository;

    public LoginService(DataJpaUserRepository dataJpaUserRepository) {
        this.dataJpaUserRepository = dataJpaUserRepository;
    }

    public User login(LoginRequest request) {
        Optional<User> user = Optional.ofNullable(dataJpaUserRepository.findByLoginIdAndPassword(request.getLoginId(), request.getPassword()));
        return user.orElse(null);
    }

    public void logout(HttpSession session) {
        if (session != null) {
            log.info(String.valueOf(session.getAttribute(SessionConst.LOGIN_USER)));
            session.invalidate();
            log.info("logout success");
        }
    }
}