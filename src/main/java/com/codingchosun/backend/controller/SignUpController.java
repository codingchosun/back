package com.codingchosun.backend.controller;

import com.codingchosun.backend.request.RegisterUserRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.service.user.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SignUpController {

    private final SignUpService signUpService;

    /**
     * 회원가입 API
     *
     * @param registerUserRequest 이름, 로그인 아이디, 비밀번호, 이메일, 성별코드, 생년월일, 닉네임
     * @param bindingResult       유효성 검사
     * @return 200 OK, true, "회원가입이 완료되었습니다."
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody @Valid RegisterUserRequest registerUserRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST, false, bindingResult.getAllErrors().get(0).getDefaultMessage()));
        }
        signUpService.signUp(registerUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED, true, "회원가입이 완료되었습니다."));
    }
}
