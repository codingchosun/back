package com.codingchosun.backend.service.user;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.dto.request.RegisterUserRequest;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.login.DuplicationLoginIdException;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
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
        dataJpaUserRepository.findByLoginId(registerUserRequest.getLoginId()).ifPresent(m -> {
                    throw new DuplicationLoginIdException(ErrorCode.LOGIN_ID_DUPLICATION);
                }
        );
        String encodedPassword = passwordEncoder.encode(registerUserRequest.getPassword());

        User user = new User(registerUserRequest);
        user.passwordEncode(encodedPassword);

        dataJpaUserRepository.save(user);
        log.info("회원 가입 성공: userId={}, loginId={}", user.getUserId(), user.getLoginId());
    }
}
