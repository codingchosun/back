package com.codingchosun.backend.controller;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.request.RegisterUserRequest;
import com.codingchosun.backend.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterUserRequest registerUserRequest
            , BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {    //검증 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("validation fail");
        }

        User signUpSuccessful = signUpService.signUp(registerUserRequest);

        if (signUpSuccessful != null) { // 회원가입 성공
            log.info("register success");
            return ResponseEntity.ok("registered successfully");
        } else {    // 회원가입 실패
            log.info("register fail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
        }
    }
}
