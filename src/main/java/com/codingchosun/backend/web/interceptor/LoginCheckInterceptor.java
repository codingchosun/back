package com.codingchosun.backend.web.interceptor;


import com.codingchosun.backend.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

//@Slf4j
//public class LoginCheckInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        String requestURI = request.getRequestURI();
//
//        log.info("로그인 체크 인터셉터 실행:{}", requestURI);
//
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
//            log.info("로그인 하세요");
//
//            response.sendRedirect("/login?redirectURL=" + requestURI);
//            return false;
//        }
//        return true;
//    }
//}
