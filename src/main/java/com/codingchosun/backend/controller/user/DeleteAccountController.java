package com.codingchosun.backend.controller.user;

import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.service.user.DeleteAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteAccountController {

    private final DeleteAccountService deleteAccountService;

    @DeleteMapping("/user/me")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        if (userDetails == null) {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다.");
        }

        deleteAccountService.deleteAccount(userDetails.getUsername());

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ApiResponse.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
    }
}
