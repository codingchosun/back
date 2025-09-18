package com.codingchosun.backend.service.user;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.login.LoginProcessFailedException;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.dto.request.LoginRequest;
import com.codingchosun.backend.dto.response.UserDTO;
import com.codingchosun.backend.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final DataJpaUserRepository dataJpaUserRepository;

    public UserDTO login(LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        } catch (AuthenticationException e) {
            log.error("로그인 실패: {}", e.getMessage());
            throw new LoginProcessFailedException(ErrorCode.LOGIN_FAILED);
        }

        User user = dataJpaUserRepository.findByLoginIdAndPassword(loginRequest.getLoginId(), loginRequest.getPassword()).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
        log.info("로그인 성공: {}", user);

        return new UserDTO(user.getUserId(), user.getLoginId(),user.getNickname());
    }

    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            log.info("로그아웃 성공: {}", session.getAttribute(SessionConst.LOGIN_USER));
            session.invalidate();
        }

        SecurityContextHolder.clearContext();
    }

    @Transactional(readOnly = true)
    public UserDTO loggedInCheck(UserDetails userDetails) {
        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }

        User user = dataJpaUserRepository.findByLoginId(userDetails.getUsername()).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );

        return new UserDTO(user.getUserId(), user.getLoginId(),user.getNickname());
    }
}