package com.codingchosun.backend.service;

import com.codingchosun.backend.constants.GenderCode;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j

public class UserUpdateServiceTest {

        private final DataJpaUserRepository userRepository;
        private final UserUpdateService userUpdateService;
        private final UserUpdateRequest updateRequest;

        @Autowired
        public UserUpdateServiceTest(DataJpaUserRepository userRepository, UserUpdateService userUpdateService, UserUpdateRequest updateRequest) {
            this.userRepository = userRepository;
            this.userUpdateService = userUpdateService;
            this.updateRequest = updateRequest;
        }

        @Test
        public void testUpdateUserInformation() {
            User updatedUser = userRepository.findByUserId(3L);
            updateRequest.setEmail("new@example.com");
            updateRequest.setPassword("newpassword");
            updateRequest.setNickname("newnickname");
            updateRequest.setIntroduction("new introduction");
            updateRequest.setGenderCode(GenderCode.NONE);
            log.info("updated user :{} ", updateRequest.toString());
            userUpdateService.updateUser(updatedUser, updateRequest);
            //데이터베이스에 잘 반영되긴하는데 테스트끝난다음에 데이터베이스에 반영되어서 테스트불가능
        }
}
