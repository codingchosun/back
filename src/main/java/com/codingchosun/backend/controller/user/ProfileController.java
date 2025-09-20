package com.codingchosun.backend.controller.user;

import com.codingchosun.backend.dto.request.ProfileResponse;
import com.codingchosun.backend.dto.request.UserUpdateRequest;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.UpdateProfileResponse;
import com.codingchosun.backend.service.user.ProfileService;
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

    @GetMapping("/{loginId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable String loginId) {
        ProfileResponse response = profileService.getProfile(loginId);

        return ApiResponse.ok(response);
    }

    @PostMapping("/me")
    public ResponseEntity<ApiResponse<UpdateProfileResponse>> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        if (userDetails == null) {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다");
        }
        UpdateProfileResponse profileResponse = profileService.updateProfile(userDetails.getUsername(), userUpdateRequest);

        return ApiResponse.ok(profileResponse);
    }

}
