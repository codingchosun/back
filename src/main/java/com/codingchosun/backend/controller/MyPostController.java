package com.codingchosun.backend.controller;

import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.MyPostResponse;
import com.codingchosun.backend.service.MyPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me")
public class MyPostController {

    private final MyPostService myPostService;
    private final DataJpaUserRepository userRepository;


    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<MyPostResponse>>> getMyPosts(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(HttpStatus.UNAUTHORIZED, false, null));
        }
        List<MyPostResponse> responseList = myPostService.getMyPosts(userDetails.getUsername());

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, true, responseList));
    }

}
