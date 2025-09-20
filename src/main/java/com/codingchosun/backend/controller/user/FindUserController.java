package com.codingchosun.backend.controller.user;

import com.codingchosun.backend.dto.request.FindLoginIdRequest;
import com.codingchosun.backend.dto.request.FindPasswordRequest;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.FindUserResponse;
import com.codingchosun.backend.service.user.FindUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class FindUserController {

    private final FindUserService findUserService;

    public FindUserController(FindUserService findUserService) {
        this.findUserService = findUserService;
    }

    @PostMapping("/login-id")
    public ResponseEntity<ApiResponse<FindUserResponse>> findUserId(@Valid @RequestBody FindLoginIdRequest request) {
        FindUserResponse response = findUserService.findMyLoginId(request);

        return ApiResponse.ok(response);
    }

    //TODO: 비밀번호 찾기 -> 이메일로 비밀번호 초기화로 변경 필요
    @PostMapping("/password")
    public ResponseEntity<ApiResponse<String>> FindPassword(@Valid @RequestBody FindPasswordRequest request) {
        return ApiResponse.ok("비밀번호 찾기 성공");
    }
}
