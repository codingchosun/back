package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.dto.response.MyPostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPostService {

    private final DataJpaPostRepository postRepository;

    public List<MyPostResponse> getMyPosts(String loginId) {
        List<Post> participatedPosts = postRepository.findParticipatedPosts(loginId);

        if (participatedPosts.isEmpty()) {
            throw new PostNotFoundFromDB("참여한 모임이 없습니다");
        }

        log.info("게시물 목록: {}", participatedPosts);

        return participatedPosts.stream()
                .map(MyPostResponse::from)
                .toList();
    }
}
