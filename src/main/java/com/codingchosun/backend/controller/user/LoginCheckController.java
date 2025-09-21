package com.codingchosun.backend.controller.user;

import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.LoginCheckResponse;
import com.codingchosun.backend.service.user.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginCheckController {

    private final LoginService loginService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<LoginCheckResponse>> getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        LoginCheckResponse loginCheckResponse = loginService.loggedInCheck(userDetails);

        return ApiResponse.ok(loginCheckResponse);
    }
}
