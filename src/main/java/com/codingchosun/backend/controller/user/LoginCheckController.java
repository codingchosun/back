package com.codingchosun.backend.controller.user;

import com.codingchosun.backend.dto.response.ApiResponse;
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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<String>> getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        loginService.loggedInCheck(userDetails);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, true, "로그인 확인 성공"));
    }
}
