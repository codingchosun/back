package com.codingchosun.backend.controller;

import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.UserDTO;
import com.codingchosun.backend.service.user.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginCheckController {

    private final LoginService loginService;

    /**
     * 로그인 유저 확인 API
     *
     * @param userDetails 세션에 유지하고 있는 유저정보
     * @return 유저 식별번호, 로그인 아이디, 이메일
     */
    @GetMapping("/api/me")
    public ResponseEntity<ApiResponse<UserDTO>> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = loginService.loggedInCheck(userDetails);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, true, userDTO));
    }
}
