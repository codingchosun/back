package com.codingchosun.backend.controller;


import com.codingchosun.backend.response.PostResponse;
import com.codingchosun.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    //작성한 모임금을 가져오는 컨트롤러
    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        PostResponse postDTO = postService.findById(postId);

        return postDTO;
    }

}
