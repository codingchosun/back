package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.request.RegisterCommentRequest;
import com.codingchosun.backend.response.CommentResponse;
import com.codingchosun.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 게시물 댓글 등록 API
     *
     * @param postId                 게시글 식별번호
     * @param userDetails            로그인 정보
     * @param registerCommentRequest 댓글내용
     * @return 201 CREATED, commentId
     */
    @PostMapping
    public ResponseEntity<Map<String, Long>> registerComment(@PathVariable Long postId,
                                                             @AuthenticationPrincipal UserDetails userDetails,
                                                             @Valid @RequestBody RegisterCommentRequest registerCommentRequest) {
        if (userDetails == null) {
            throw new LoggedInUserNotFound("로그인을 해야 댓글을 작성할수있습니다");
        }
        Comment comment = commentService.registerComments(postId, userDetails.getUsername(), registerCommentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("commentId", comment.getCommentId()));
    }

    /**
     * 특정 게시물의 댓글 조회 API
     *
     * @param postId     게시물 식별번호
     * @param pageNumber 조회수
     * @param pageSize   조회페이지
     * @return 댓글목록
     */
    @GetMapping
    public ResponseEntity<Page<CommentResponse>> getPostComments(@PathVariable Long postId,
                                                                 @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                                 @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize) {
        Page<CommentResponse> comments = commentService.getPagedComments(PageRequest.of(pageNumber, pageSize), postId);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    /**
     * 댓글 삭제 API
     *
     * @param userDetails 로그인 정보
     * @param commentId   댓글 식별키
     * @return 200 OK, "댓글이 성공적을 삭제되었습니다"
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComments(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long commentId) {
        commentService.deleteComment(commentId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body("댓글이 성공적을 삭제되었습니다");
    }

}
