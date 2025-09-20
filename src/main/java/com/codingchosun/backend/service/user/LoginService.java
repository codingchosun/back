package com.codingchosun.backend.service.user;

import com.codingchosun.backend.dto.request.LoginRequest;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.login.LoginProcessFailedException;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final DataJpaUserRepository dataJpaUserRepository;

    public void login(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("로그인 성공: {}", authentication.getName());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            dataJpaUserRepository.findByLoginId(userDetails.getUsername()).orElseThrow(
                    () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
            );
        } catch (AuthenticationException e) {
            log.error("로그인 실패: {}", e.getMessage());
            throw new LoginProcessFailedException(ErrorCode.LOGIN_FAILED);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    @Transactional(readOnly = true)
    public void loggedInCheck(UserDetails userDetails) {
        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }

        dataJpaUserRepository.findByLoginId(userDetails.getUsername()).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
    }
}