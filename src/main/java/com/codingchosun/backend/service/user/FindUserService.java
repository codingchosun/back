package com.codingchosun.backend.service.user;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.request.FindLoginIdRequest;
import com.codingchosun.backend.request.FindPasswordRequest;
import com.codingchosun.backend.response.FindUserResponse;
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
                () -> new EntityNotFoundFromDB("아이디 찾기에 해당하는 유저를 찾을 수 없습니다")
        );
        log.info("아이디 찾기 성공: {}", user);

        return new FindUserResponse(user.getUserId(), user.getLoginId());
    }

    public String findMyPassword(FindPasswordRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String id = request.getLoginId();
        User user = userRepository.findByNameAndEmailAndLoginId(name, email, id).orElseThrow(
                () -> new EntityNotFoundFromDB("비밀번호 찾기에 해당 유저를 찾을 수 없습니다")
        );
        log.info("비밀번호 찾기 성공: {}", user);

        return user.getPassword();

    }
}
