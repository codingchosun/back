package com.codingchosun.backend.controller;

import com.codingchosun.backend.request.FindLoginIdRequest;
import com.codingchosun.backend.request.FindPasswordRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.service.user.FindUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class FindUserController {

    private final FindUserService findUserService;

    public FindUserController(FindUserService findUserService) {
        this.findUserService = findUserService;
    }

    /**
     * 이름과 이메일로 사용자 아이디 조회 (쿼리 파라미터 노출 방지를 위해 데이터를 Body 에 보냄)
     * @param request 이름, 이메일
     * @return 로그인 아이디
     */
    @PostMapping("/login-id")
    public ApiResponse<Map<String, String>> findUserId(@RequestBody FindLoginIdRequest request) {
        Map<String, String> response = Map.of("loginId", findUserService.findMyLoginId(request));
        return new ApiResponse<>(HttpStatus.OK, true, response);
    }

    @PostMapping("/password")
    public ApiResponse<String> FindPassword(@RequestBody FindPasswordRequest request) {
        return new ApiResponse<>(HttpStatus.OK, true, findUserService.findMyPassword(request));
    }
}
