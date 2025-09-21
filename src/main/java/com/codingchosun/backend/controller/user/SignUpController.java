package com.codingchosun.backend.controller.user;

import com.codingchosun.backend.dto.request.RegisterUserRequest;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.service.user.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Valid RegisterUserRequest registerUserRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        signUpService.signUp(registerUserRequest);

        return ApiResponse.created("회원가입이 완료되었습니다.");
    }
}
