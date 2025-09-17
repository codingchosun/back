package com.codingchosun.backend.controller;

import com.codingchosun.backend.service.user.DeleteAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteAccountController {

    DeleteAccountService deleteAccountService;

    /**
     * 회원탈퇴 API (유저의 StateCode 만 변경)
     *
     * @param userDetails 로그인 유저
     * @param request     HTTP 요청
     * @return 401 UNAUTHORIZED, "인증 정보가 없습니다."
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteAccount(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 정보가 없습니다.");
        }

        deleteAccountService.deleteAccount(userDetails.getUsername());

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴가 성공적으로 처리되었습니다.");
    }
}
