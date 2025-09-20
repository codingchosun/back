package com.codingchosun.backend.controller.post;


import com.codingchosun.backend.dto.request.PostRegistrationRequest;
import com.codingchosun.backend.dto.request.PostRemoveRequest;
import com.codingchosun.backend.dto.request.PostUpdateRequest;
import com.codingchosun.backend.dto.response.*;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.service.post.PostReadService;
import com.codingchosun.backend.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostReadService postReadService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostRegistrationResponse>> registerPost(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid PostRegistrationRequest postRegistrationRequest) {

        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }

        PostRegistrationResponse postRegistrationResponse = postService.registerPost(postRegistrationRequest, userDetails.getUsername());

        return ApiResponse.created(postRegistrationResponse);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostUpdateResponse>> updatePost(@RequestBody PostUpdateRequest postUpdateRequest,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        PostUpdateResponse postUpdateResponse = postService.updatePost(Long.valueOf(postUpdateRequest.getPostId()), userDetails.getUsername(), postUpdateRequest);

        return ApiResponse.ok(postUpdateResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable Long postId,
                                                          @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        String message = postService.deletePost(new PostRemoveRequest(postId), userDetails.getUsername());

        return ApiResponse.ok(message);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        postService.increaseViewCount(postId);
        PostResponse postResponse = postReadService.getPostResponse(postId);

        return ApiResponse.ok(postResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getPostLists(@AuthenticationPrincipal UserDetails userDetails, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        if (userDetails != null) {
            LoginPostsHashtagResponse response = postReadService.loginPostsRequests(userDetails.getUsername(), pageable);
            return ApiResponse.ok(response);
        } else {
            NoLoginPostsHashtagsResponse response = postReadService.noLoginGetPosts(pageable);
            return ApiResponse.ok(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<SearchPostResponse>>> searchPost(@RequestParam(value = "search", required = false, defaultValue = "") String search, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SearchPostResponse> response = postReadService.searchPost(search, pageable);

        return ApiResponse.ok(response);
    }

}
