package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.UserValidationRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.response.UserDTO;
import com.codingchosun.backend.service.PostService;
import com.codingchosun.backend.service.PostUserService;
import com.codingchosun.backend.service.ValidateService;
import com.codingchosun.backend.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mypost")
public class ValidateControllerV2 {

    private final PostUserService postUserService;
    private final PostService postService;
    private final ValidateService validateService;
    private final DataJpaUserRepository dataJpaUserRepository;

    @GetMapping("/{postId}")
    public List<UserDTO> getPostParticipants(@PathVariable Long postId,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        //로그인 검사
        if(userDetails == null){
            throw new LoggedInUserNotFound("로그인해주세요");
        }
        User user = this.getUserFromUserDetails(userDetails);

        List<UserDTO> participants = postUserService.getParticipants(postId);

        //로그인한 유저 빼기
        Long userId = user.getUserId();
        participants = participants.stream()
                .filter(participant -> !participant.getUserId().equals(userId))
                .collect(Collectors.toList());

        return participants;
    }

    @PostMapping("/{postId}")
    public ApiResponse<Integer> validateParticipants(@RequestBody Map<String, List<UserValidationRequest>> userValidationRequestMap,
                                                     @PathVariable Long postId,
                                                     @AuthenticationPrincipal UserDetails userDetails) {

        //로그인 검사
        if(userDetails == null){
            throw new LoggedInUserNotFound("로그인해주세요");
        }
        User user = this.getUserFromUserDetails(userDetails);

        //포스트 검사
        Post post = postService.getPost(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("포스트를 못 찾겠습니다"));

        //평가하기
        int cnt = validateService.validateUser(post, user, userValidationRequestMap.get("userValidationRequestList"));

        return new ApiResponse<>(HttpStatus.OK,true,cnt);   //평가한 개수 리턴
    }

    public User getUserFromUserDetails(UserDetails userDetails){
        return dataJpaUserRepository.findByLoginId(userDetails.getUsername());
    }
}
