package com.codingchosun.backend.controller;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.request.LoginRequest;
import com.codingchosun.backend.service.LoginService;
import com.codingchosun.backend.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult,
            HttpServletRequest request)
    {
        //log.info("Received login request: loginId={}, password={}", loginRequest.getLoginId(), loginRequest.getPassword());
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User loginUser = loginService.login(loginRequest);

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            log.info("login fail");
        } else {
            log.info("login success! loginUser: {}", loginUser.getLoginId());

            HttpSession session = request.getSession();
            log.info(String.valueOf(session.getCreationTime()));
            session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        loginService.logout(session);

        if (session != null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
