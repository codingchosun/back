package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.MyPostResponse;
import com.codingchosun.backend.service.MyPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MypostController {

    private final MyPostService myPostService;
    private  final DataJpaUserRepository userRepository;

    @Autowired
    public MypostController(MyPostService myPostService, DataJpaUserRepository userRepository) {
        this.myPostService = myPostService;
        this.userRepository = userRepository;
    }

    @GetMapping("/mypost")
    public ApiResponse<List<MyPostResponse>> mypost(@AuthenticationPrincipal UserDetails user, Model model) {
        User loginUser = getUserFromUserDetails(user);
        log.info("login user:{}", user);
        List<MyPostResponse> responseList = myPostService.getMyPost(loginUser);

        if (responseList == null || responseList.isEmpty()) {
            throw new PostNotFoundFromDB("참여한 모임이 없습니다");
        }

        return new ApiResponse<>(HttpStatus.OK, true, responseList);
    }


    public User getUserFromUserDetails(UserDetails userDetails){
        return userRepository.findByLoginId(userDetails.getUsername());
    }
}
