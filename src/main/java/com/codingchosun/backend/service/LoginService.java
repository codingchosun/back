package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.LoginRequest;
import com.codingchosun.backend.web.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

    public void logout(HttpSession session) {
        if (session != null) {
            log.info(String.valueOf(session.getAttribute(SessionConst.LOGIN_USER)));
            session.invalidate();
            log.info("logout success");
        }
    }
}