package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.response.MyPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j

public class MyPostServiceTest {

    private final MyPostService myPostService;
    private final DataJpaUserRepository dataJpaUserRepository;

    @Autowired
    public MyPostServiceTest(MyPostService myPostService, DataJpaUserRepository dataJpaUserRepository) {
        this.myPostService = myPostService;
        this.dataJpaUserRepository = dataJpaUserRepository;
    }


    @Test
    public void getMyPostsTest() {

        User user = dataJpaUserRepository.findByUserId(10000L);
        log.info("user: {}", user);

        List<MyPostResponse> post = myPostService.getMyPost(user);
        for(MyPostResponse myPostResponse : post){
            log.info(myPostResponse.toString());
        }
        assertNotNull(post);
    }
}