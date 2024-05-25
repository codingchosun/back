package com.codingchosun.backend.controller;


import com.codingchosun.backend.constants.PagingConstants;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.request.RegisterPostRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.NoLoginPostsRequest;
import com.codingchosun.backend.response.CommentResponse;
import com.codingchosun.backend.response.PostResponse;
import com.codingchosun.backend.service.CommentService;
import com.codingchosun.backend.service.PostService;
import com.codingchosun.backend.web.argumentresolver.Login;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

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

    // 로그인 안 했을 때 글 보기
    @GetMapping
    public HttpEntity<Page<NoLoginPostsRequest>> NoLoginShowPosts(@RequestParam("page") int page,
                                                                    @RequestParam("size") int size
                                                                    )
    {
        return new ResponseEntity<>(postService.noLoginGetPosts(page, size), HttpStatus.OK);
    }
    //TODO 로그인 했을 때 글 보기

    @Data
    public static class PostAndComments{
        private PostResponse postResponse;
        private List<CommentResponse> commentResponseList;
        //todo 이미지 추가

    }

}
