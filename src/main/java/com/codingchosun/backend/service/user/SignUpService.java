package com.codingchosun.backend.service.user;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.login.DuplicationLoginIdException;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.dto.request.RegisterUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final DataJpaUserRepository dataJpaUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(RegisterUserRequest registerUserRequest) {
        if (dataJpaUserRepository.findByLoginId(registerUserRequest.getLoginId()).isPresent()) {
            throw new DuplicationLoginIdException(ErrorCode.LOGIN_ID_DUPLICATION);
        }

        User user = new User(registerUserRequest);
        log.info("회원 가입 성공: {}", user.getUserId());

        String encodedPassword = passwordEncoder.encode(registerUserRequest.getPassword());
        user.passwordEncode(encodedPassword);

        dataJpaUserRepository.save(user);
    }
}
