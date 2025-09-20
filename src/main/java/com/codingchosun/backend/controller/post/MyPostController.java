package com.codingchosun.backend.controller.post;

import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.MyPostResponse;
import com.codingchosun.backend.service.post.PostQueryService;
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
@RequestMapping("/api")
public class MyPostController {

    private final PostQueryService postQueryService;

    @GetMapping("/me/posts")
    public ResponseEntity<ApiResponse<List<MyPostResponse>>> getMyPosts(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다");
        }
        List<MyPostResponse> responseList = postQueryService.getMyPosts(userDetails.getUsername());

        return ApiResponse.ok(responseList);
    }


}
