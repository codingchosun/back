package com.codingchosun.backend.controller;

import com.codingchosun.backend.response.UserDTO;
import com.codingchosun.backend.service.PostParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    //특정 모임의 참가자 목록 조회 api
    @GetMapping("/{postId}/participants")
    public ResponseEntity<List<UserDTO>> getParticipatePost(@PathVariable Long postId) {
        List<UserDTO> participants = postParticipantService.getParticipants(postId);

        return ResponseEntity.status(HttpStatus.OK).body(participants);
    }

    //로그인 유저가 모임 참가 api
    @PostMapping("/{postId}/participants")
    public ResponseEntity<String> participatePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        postParticipantService.participatePost(postId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body("모임 참여 완료");
    }

    //모임 참여 유저 탈퇴 api
    @DeleteMapping("/me")
    public ResponseEntity<String> leavePost(@RequestParam Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        postParticipantService.leavePost(postId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body("모임 탈퇴 완료");
    }

    //모임 유저 추방 api
    @DeleteMapping("/{banishUserId}")
    public ResponseEntity<String> banishPost(@RequestParam Long postId, @RequestParam Long banishUserId, @AuthenticationPrincipal UserDetails userDetails) {
        postParticipantService.banishPost(postId, banishUserId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body("해당 유저를 모임에서 추방했습니다.");
    }
}
