package com.codingchosun.backend.service.user;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.request.FindLoginIdRequest;
import com.codingchosun.backend.request.FindPasswordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FindUserService {

    private final DataJpaUserRepository userRepository;

    public FindUserService(DataJpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String findMyLoginId(FindLoginIdRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        User user = userRepository.findByNameAndEmail(name, email).orElseThrow(
                () -> new EntityNotFoundFromDB("아이디 찾기에 해당하는 유저를 찾을 수 없습니다")
        );

        return user.getLoginId();
    }

    public String findMyPassword(FindPasswordRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String id = request.getLoginId();
        User user = userRepository.findByNameAndEmailAndLoginId(name, email, id).orElseThrow(
                () -> new EntityNotFoundFromDB("비밀번호 찾기에 해당 유저를 찾을 수 없습니다")
        );

        return user.getPassword();

    }
}
