package com.codingchosun.backend.controller;


import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.dto.request.PostUpdateRequest;
import com.codingchosun.backend.dto.request.RegisterPostRequest;
import com.codingchosun.backend.dto.response.LoginPostsHashtagResponse;
import com.codingchosun.backend.dto.response.NoLoginPostsHashtagsResponse;
import com.codingchosun.backend.dto.response.PostResponse;
import com.codingchosun.backend.dto.response.SearchPostResponse;
import com.codingchosun.backend.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostReadService postReadService;

    private final CommentService commentService;
    private final ImageService imageService;
    private final PostParticipantService postParticipantService;
    private final DataJpaUserRepository dataJpaUserRepository;

    /**
     * 게시물 작성 API
     *
     * @param userDetails         로그인 정보
     * @param registerPostRequest 제목, 내용, 시작시간, 해시태그 목록
     * @return 201 CREATED, 게시물 식별 번호
     */
    @PostMapping
    public ResponseEntity<Map<String, Long>> registerPost(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid RegisterPostRequest registerPostRequest) {

        if (userDetails == null) {
            throw new LoggedInUserNotFound("게시물 작성은 로그인이 필수입니다.");
        }
        //2024.06.18 타임존 설정에 실패하고 시간에 +9를 더하기로 결정 -> TODO: 타임존 적용방법 찾기
        registerPostRequest.setStartTime(registerPostRequest.getStartTime().plusHours(9));
        log.info("시작 시간: {}", registerPostRequest.getStartTime());

        Post registerPost = postService.registerPost(registerPostRequest, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("postId", registerPost.getPostId()));
    }

    /**
     * 게시물 수정 API
     *
     * @param postId            게시물 식별번호
     * @param postUpdateRequest 제목, 내용, 시작시간, 해시태그 목록
     * @param userDetails       로그인 정보
     * @return 200 OK, 게시물 식별 번호
     */
    @PatchMapping("/{postId}")
    public ResponseEntity<Map<String, Long>> editPost(@PathVariable Long postId,
                                                      @RequestBody PostUpdateRequest postUpdateRequest,
                                                      @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            throw new LoggedInUserNotFound("게시물 수정은 로그인이 필수입니다.");
        }
        //마찬가지로 타임존 설정 필요
        Post editPost = postService.editPost(postId, userDetails.getUsername(), postUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("postId", editPost.getPostId()));
    }

    /**
     * 게시물 삭제(비활성화) API
     *
     * @param postId      게시물 식별번호
     * @param userDetails 로그인정보
     * @return 200 OK, "삭제 완료"
     */
    @DeleteMapping("/{postId}")
    public HttpEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            throw new LoggedInUserNotFound("삭제는 로그인이 필수입니다.");
        }
        String message = postService.deletePost(postId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    /**
     * 특정 게시물 상세 조회 API
     *
     * @param postId 게시물 식별번호
     * @return 게시물 식별번호, 제목, 내용, 생성일, 시작일, 상태 코드, 조회수, 게시글 작성자 식별번호, 게시글 작성자 닉네임, 해시태그 목록
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        postService.increaseViewCount(postId);
        PostResponse postResponse = postReadService.getPostResponse(postId);

        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }

    /**
     * 게시물 목록 조회(로그인/비로그인) API
     *
     * @param userDetails 로그인 정보
     * @param pageable    페이징 정보
     * @return 200 OK, 페이징 처리된 게시물 목록, 해시태그 목록
     */
    @GetMapping
    public ResponseEntity<?> getPostLists(@AuthenticationPrincipal UserDetails userDetails, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        if (userDetails != null) {
            LoginPostsHashtagResponse response = postReadService.loginPostsRequests(userDetails.getUsername(), pageable);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            NoLoginPostsHashtagsResponse response = postReadService.noLoginGetPosts(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    /**
     * 게시물 검색 API
     *
     * @param search   검색할 단어
     * @param pageable 페이징 정보
     * @return 200 OK, 게시물 식별번호, 제목, 내용, 이미지 경로
     */
    @GetMapping("/search")
    public ResponseEntity<Page<SearchPostResponse>> searchPost(@RequestParam(value = "search", required = false, defaultValue = "") String search, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SearchPostResponse> response = postReadService.searchPost(search, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
