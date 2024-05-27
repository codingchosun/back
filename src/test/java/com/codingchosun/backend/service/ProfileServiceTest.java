package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.UserRepository;
import com.codingchosun.backend.request.ProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ProfileServiceTest {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    @Autowired
    public ProfileServiceTest(ProfileService profileService, UserRepository userRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
    }
    //  resources/sql/profiletest.sql
    @Test
    public void profileTest() {
        User user = userRepository.findByUserId(10000L);
        ProfileResponse p = profileService.getProfile(user.getNickname());
        log.info("닉네임={}", p.getNickname());
        log.info("이메일={}",p.getEmail());
        log.info("자기소개={}",p.getIntroduction());
        log.info("score={}", p.getScore());
        log.info("Hashtag Names:");

        for (String hashtagName : p.getHashNames()) {
            log.info(hashtagName + ", ");
        }

        log.info("Template Names : ");
        for (String templateNames : p.getTemplateNames()) {
            log.info(templateNames);
        }

        Assertions.assertNotNull(p);
    }
}
