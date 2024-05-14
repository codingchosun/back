package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final DataJpaPostRepository dataJpaPostRepository;

    public PostResponse findById(Long postId) {

        Post post;

        try {
            post = dataJpaPostRepository.findById(postId).orElseThrow();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("Post ID: " + postId + "를 찾지 못했습니다.");
        }

        return new PostResponse(post);
    }

    //검증 필요 + state_code가 active인것만 검색되게 할지 결정해야함
    public List<PostResponse> findByTitle(String title) {
        List<Post> posts = dataJpaPostRepository.findByTitle(title);
        return posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
