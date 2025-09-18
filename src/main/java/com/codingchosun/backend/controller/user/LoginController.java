package com.codingchosun.backend.controller.user;

import com.codingchosun.backend.dto.request.LoginRequest;
import com.codingchosun.backend.service.user.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인 API
     *
     * @param loginRequest       로그인 아이디, 비밀번호
     * @param httpServletRequest HTTP 요청
     * @return 200 OK, "로그인 성공"
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        loginService.login(loginRequest, httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }

    /**
     * 로그아웃 API
     *
     * @param httpServletRequest HTTP 요청
     * @return 200 OK, "로그아웃 성공"
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        loginService.logout(httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }
}
