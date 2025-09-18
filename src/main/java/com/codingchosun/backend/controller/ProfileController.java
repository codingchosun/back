package com.codingchosun.backend.controller;

import com.codingchosun.backend.dto.request.ProfileResponse;
import com.codingchosun.backend.dto.request.UserUpdateRequest;
import com.codingchosun.backend.dto.response.UpdateProfileResponse;
import com.codingchosun.backend.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 조회 API
     *
     * @param loginId 로그인 아이디
     * @return 200 OK, 닉네임, 자기소개, 이메일, 점수, 해시태그, 템플릿 내용
     */
    @GetMapping("/{loginId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String loginId) {
        ProfileResponse response = profileService.getProfile(loginId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 프로필 수정 API
     *
     * @param userDetails 로그인 정보
     * @param userUpdateRequest 현재 비밀번호, 새 비밀번호, 이메일, 성별코드, 닉네임, 자기소개, 해시태그목록
     * @return 200 OK, 이메일, 성별코드, 닉네임, 자기소개, 해시태그 목록
     */
    @PostMapping("/me")
    public ResponseEntity<UpdateProfileResponse> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        UpdateProfileResponse profileResponse = profileService.updateProfile(userDetails.getUsername(), userUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(profileResponse);
    }

}
