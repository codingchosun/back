package com.codingchosun.backend.controller.comment;

import com.codingchosun.backend.dto.request.RegisterCommentRequest;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.CommentRegistrationResponse;
import com.codingchosun.backend.dto.response.CommentResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentRegistrationResponse>> registerComment(@PathVariable Long postId,
                                                                                    @AuthenticationPrincipal UserDetails userDetails,
                                                                                    @Valid @RequestBody RegisterCommentRequest registerCommentRequest) {
        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        CommentRegistrationResponse commentRegistrationResponse = commentService.registerComments(postId, userDetails.getUsername(), registerCommentRequest);

        return ApiResponse.created(commentRegistrationResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> getPostComments(@PathVariable Long postId,
                                                                              @PageableDefault(size = 20) Pageable pageable) {
        Page<CommentResponse> comments = commentService.getPagedComments(pageable, postId);

        return ApiResponse.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<String>> deleteComments(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        commentService.deleteComment(postId, commentId, userDetails.getUsername());

        return ApiResponse.ok("댓글이 성공적을 삭제되었습니다");
    }

}
