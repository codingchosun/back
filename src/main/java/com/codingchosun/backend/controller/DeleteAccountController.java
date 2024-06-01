package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.service.DeleteAccountService;
import com.codingchosun.backend.web.argumentresolver.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteAccountController {

    DeleteAccountService deleteAccountService;

    @Autowired
    public DeleteAccountController(DeleteAccountService deleteAccountService) {
        this.deleteAccountService = deleteAccountService;
    }

    @GetMapping("/deleteAccount")
    public ApiResponse<String> deleteAccount(@Login User user) {
        deleteAccountService.deleteAccount(user);
        return new ApiResponse<>(HttpStatus.ACCEPTED, true, "회원탈퇴 성공");
    }
}
