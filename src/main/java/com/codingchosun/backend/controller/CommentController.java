package com.codingchosun.backend.controller;

import com.codingchosun.backend.constants.ExceptionConstants;
import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.exception.emptyrequest.EmptyCommentException;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.request.RegisterCommentRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.CommentResponse;
import com.codingchosun.backend.service.CommentService;
import com.codingchosun.backend.service.PostService;
import com.codingchosun.backend.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public ApiResponse<Long> registerComment(@Login User user, @PathVariable Long postId,
                                             @RequestBody RegisterCommentRequest registerCommentRequest){

        //user 못 가져올경우
        if(user == null){
            throw new LoggedInUserNotFound("로그인을 해야 댓글을 작성할수있습니다");
        }

        //빈 댓글 예외처리
        if(registerCommentRequest.getContents() == null){
            throw new EmptyCommentException(ExceptionConstants.EMPTY_COMMENT);
        }

        //포스트 가져오며 예외처리
        Post post = postService.getPost(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("postId: " + postId + "를 찾지 못했습니다"));

        Comment comment = commentService.registerComments(user, post, registerCommentRequest);

        return new ApiResponse<>(HttpStatus.OK, true, comment.getCommentId());
    }

    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResponse> getPostComments(@PathVariable Long postId,
                                                 @RequestParam(required = false, defaultValue = "1", value = "pageNo") int pageNo,
                                                 @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria){

        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by(Sort.Direction.DESC, criteria));

        return commentService.getPagedComments(pageable, postId);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public HttpEntity<String> deleteComments(@Login User user,
                                              @PathVariable Long postId,
                                             @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.deleteComment(user, postId, commentId), HttpStatus.OK);
    }

}
