package com.codingchosun.backend.controller;

import com.codingchosun.backend.request.FindLogginIdRequest;
import com.codingchosun.backend.request.FindPasswordRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.service.FindUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindUserController {

    FindUserService findUserService;

    @Autowired
    public FindUserController(FindUserService findUserService) {
        this.findUserService = findUserService;
    }

    @PostMapping("/findId")
    public ApiResponse<String> FindUserId(@RequestBody FindLogginIdRequest request) {
        return new ApiResponse<>(HttpStatus.OK, true, findUserService.findMyLoginId(request));
    }

    @PostMapping("/findpassword")
    public  ApiResponse<String> FindPassword(@RequestBody FindPasswordRequest request) {
        return new ApiResponse<>(HttpStatus.OK, true, findUserService.findMyPassword(request));
    }
}
