package com.codingchosun.backend.controller;


import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.request.RegisterPostRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.PostResponse;
import com.codingchosun.backend.service.PostService;
import com.codingchosun.backend.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    //작성한 모임글의 내용만 가져오는 컨트롤러
    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        return postService.getPostResponse(postId);
    }

    //게시글 작성
    @PostMapping("/register")
    public ApiResponse<Long> registerPost(@RequestBody RegisterPostRequest registerPostRequest, BindingResult bindingResult,
                                    @Login User user){
        Post registeredPost = postService.registerPost(registerPostRequest, user);
        return new ApiResponse<>(HttpStatus.OK, true, registeredPost.getPostId());
    }

}
