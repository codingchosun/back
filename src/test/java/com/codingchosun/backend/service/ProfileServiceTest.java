package com.codingchosun.backend.service;

import com.codingchosun.backend.request.ProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Test
    public void profileTest() {
        ProfileResponse p = profileService.getProfile("kim");
        log.info(p.getNickname());
        log.info(p.getEmail());
        log.info(p.getIntroduction());
        log.info("score={}", p.getScore());
        log.info("Hashtag Names:");

        for (String hashtagName : p.getHashNames()) {
            log.info(hashtagName + ", ");
        }

        log.info("Template Names : ");
        for (String templateNames : p.getTemplateNames()) {
            log.info(templateNames + ", ");
        }

        Assertions.assertNotNull(p);
    }

// INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (1 ,'#광주광역시')
// INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (2 ,'#지원1동')
//INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (3 ,'#소태역')
//INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 1);
//INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 2);
//INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 3);
// INSERT INTO template (score, content) VALUES (2, '이 집 잘하네');
// INSERT INTO template (score, content) VALUES (10, '밥경찰');
// INSERT INTO template (score, content) VALUES (33, '재미없어요');
}
