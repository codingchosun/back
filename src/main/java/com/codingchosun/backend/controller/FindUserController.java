package com.codingchosun.backend.controller;

import com.codingchosun.backend.dto.request.FindLoginIdRequest;
import com.codingchosun.backend.dto.request.FindPasswordRequest;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.FindUserResponse;
import com.codingchosun.backend.service.user.FindUserService;
import org.springframework.http.HttpStatus;
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

    /**
     * 이름과 이메일로 사용자 아이디 조회 (쿼리 파라미터 노출 방지를 위해 데이터를 Body 에 보냄)
     *
     * @param request 이름, 이메일
     * @return 유저 식별번호, 로그인 아이디
     */
    @PostMapping("/login-id")
    public ApiResponse<FindUserResponse> findUserId(@RequestBody FindLoginIdRequest request) {
        FindUserResponse response = findUserService.findMyLoginId(request);

        return new ApiResponse<>(HttpStatus.OK, true, response);
    }

    //TODO: 비밀번호 찾기 -> 이메일로 비밀번호 초기화로 변경 필요
    @PostMapping("/password")
    public ApiResponse<String> FindPassword(@RequestBody FindPasswordRequest request) {
        return new ApiResponse<>(HttpStatus.OK, true, findUserService.findMyPassword(request));
    }
}
