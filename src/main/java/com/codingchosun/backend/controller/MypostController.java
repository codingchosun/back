package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.MyPostResponse;
import com.codingchosun.backend.service.MyPostService;
import com.codingchosun.backend.web.argumentresolver.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MypostController {

    MyPostService myPostService;

    @Autowired
    public MypostController(MyPostService myPostService) {
        this.myPostService = myPostService;
    }

    @GetMapping("/mypost")
    public ApiResponse<List<MyPostResponse>> mypost(@Login User user, Model model) {
        log.info("login user:{}", user);
        List<MyPostResponse> responseList = myPostService.getMyPost(user);
        return new ApiResponse<>(HttpStatus.OK, true, responseList);
    }
}
