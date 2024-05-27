package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public MyPostServiceTest(MyPostService myPostService, UserRepository userRepository) {
        this.myPostService = myPostService;
        this.userRepository = userRepository;
    }


    @Test
    public void getMyPostsTest() {
        User user = userRepository.findByUserId(1L);
        List<MyPostResponse> post = myPostService.getMyPost(user);
        for(MyPostResponse myPostResponse : post){
            log.info(myPostResponse.toString());
        }
        assertNotNull(post);
    }
}