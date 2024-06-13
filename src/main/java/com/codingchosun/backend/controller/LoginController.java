package com.codingchosun.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

@Slf4j
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(
//            @RequestBody LoginRequest loginRequest,
//            BindingResult bindingResult,
//            HttpServletRequest request)
//    {
//        //log.info("Received login request: loginId={}, password={}", loginRequest.getLoginId(), loginRequest.getPassword());
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        User loginUser = loginService.login(loginRequest);
//
//        if (loginUser == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            log.info("login fail");
//        } else {
//            log.info("login success! loginUser: {}", loginUser.getLoginId());
//
//            HttpSession session = request.getSession();
//            log.info(String.valueOf(session.getCreationTime()));
//            session.setAttribute(SessionConst.LOGIN_USER, loginUser);
//
//            log.info("login success! session: {}", session.getId());
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String loginId, @RequestParam String password, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession().setAttribute("SPRING_SECURITY", SecurityContextHolder.getContext());
            return ResponseEntity.ok("로그인 성공");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }
}

//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//
//        HttpSession session = request.getSession(false);
//        loginService.logout(session);
//
//        if (session != null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//}
