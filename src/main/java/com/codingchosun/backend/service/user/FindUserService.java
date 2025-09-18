package com.codingchosun.backend.service.user;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.dto.request.FindLoginIdRequest;
import com.codingchosun.backend.dto.request.FindPasswordRequest;
import com.codingchosun.backend.dto.response.FindUserResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindUserService {

    private final DataJpaUserRepository userRepository;

    public FindUserResponse findMyLoginId(FindLoginIdRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        User user = userRepository.findByNameAndEmail(name, email).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
        log.info("아이디 찾기 성공: {}", user.getLoginId());

        return new FindUserResponse(user.getUserId(), user.getLoginId());
    }

    public String findMyPassword(FindPasswordRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String id = request.getLoginId();
        User user = userRepository.findByNameAndEmailAndLoginId(name, email, id).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
        log.info("비밀번호 찾기 성공: {}", user.getPassword());

        return user.getPassword();

    }
}
