package com.codingchosun.backend.controller.post;

import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.UserDTO;
import com.codingchosun.backend.service.post.PostParticipantService;
import com.codingchosun.backend.service.post.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostParticipantController {

    private final PostParticipantService postParticipantService;
    private final PostQueryService postQueryService;

    @GetMapping("/{postId}/participants")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getParticipatePost(@PathVariable Long postId) {
        List<UserDTO> participants = postQueryService.getParticipants(postId);

        return ApiResponse.ok(participants);
    }

    @PostMapping("/{postId}/participants")
    public ResponseEntity<ApiResponse<String>> participatePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        postParticipantService.participatePost(postId, userDetails.getUsername());

        return ApiResponse.ok("모임 참여에 완료하였습니다.");
    }

    @DeleteMapping("/{postId}/exit")
    public ResponseEntity<ApiResponse<String>> exitPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        postParticipantService.leavePost(postId, userDetails.getUsername());

        return ApiResponse.ok("모임 탈퇴에 성공하였습니다.");
    }

    @DeleteMapping("/{postId}/participants/{banishUserId}")
    public ResponseEntity<ApiResponse<String>> banishPost(@PathVariable Long postId, @PathVariable Long banishUserId, @AuthenticationPrincipal UserDetails userDetails) {
        postParticipantService.banishPost(postId, banishUserId, userDetails.getUsername());

        return ApiResponse.ok("모임에서 유저 추방을 성공하였습니다.");
    }
}
