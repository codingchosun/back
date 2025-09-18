package com.codingchosun.backend.service.user;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeleteAccountService {

    DataJpaUserRepository userRepository;

    public void deleteAccount(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new NotAuthenticatedException(ErrorCode.USER_NOT_FOUND)
        );

        user.setStateCode(StateCode.INACTIVE);

        log.info("탈퇴 유저 비활성화 완료: {}", loginId);
    }

}
