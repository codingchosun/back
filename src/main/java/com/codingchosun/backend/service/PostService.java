package com.codingchosun.backend.service;

import com.codingchosun.backend.constants.DeleteConstants;
import com.codingchosun.backend.domain.Hashtag;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.invalidtime.BeforeCurrentTimeException;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.hashtag.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuser.DataJpaPostUserRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.dto.request.PostUpdateRequest;
import com.codingchosun.backend.dto.request.RegisterPostRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final DataJpaPostRepository postRepository;
    private final DataJpaPostUserRepository postUserRepository;
    private final DataJpaHashtagRepository hashtagRepository;
    private final DataJpaUserRepository userRepository;

    //게시물 생성
    public Post registerPost(RegisterPostRequest registerPostRequest, String loginId) {
        User user = findUserByLoginId(loginId);

        validateStartTime(registerPostRequest.getStartTime());

        Post post = new Post(
                registerPostRequest.getTitle(),
                registerPostRequest.getContent(),
                registerPostRequest.getStartTime(),
                user
        );

        PostUser postUser = new PostUser(user, post);
        post.getPostUsers().add(postUser);
        postUserRepository.save(postUser);

        List<Hashtag> hashtags = findOrCreateHashtags(registerPostRequest.getHashtags());
        post.updateHashtags(hashtags);

        return postRepository.save(post);
    }

    //게시물 수정
    public Post editPost(Long postId, String loginId, PostUpdateRequest postUpdateRequest) {
        Post post = findPostById(postId);
        User user = findUserByLoginId(loginId);

        post.validateOwner(user);
        validateStartTime(postUpdateRequest.getStartTime());
        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContent(), postUpdateRequest.getStartTime());

        List<Hashtag> hashtags = findOrCreateHashtags(postUpdateRequest.getHashtags());
        post.updateHashtags(hashtags);

        return post;
    }

    //게시물 삭제
    public String deletePost(Long postId, String loginId) {
        Post post = findPostById(postId);
        User user = findUserByLoginId(loginId);

        post.validateOwner(user);
        post.deletePost();

        return DeleteConstants.DELETE_COMPLETE;
    }

    //게시물 조회수 증가
    public void increaseViewCount(Long postId) {
        findPostById(postId).increaseViewCount();
    }

    private List<Hashtag> findOrCreateHashtags(List<String> hashtagNames) {
        return hashtagNames.stream()
                .map(name -> {
                    String tagName = name.startsWith("#") ? name.substring(1) : name;

                    return hashtagRepository.findByHashtagName(tagName).orElseGet(
                            () -> hashtagRepository.save(new Hashtag(tagName))
                    );
                })
                .collect(Collectors.toList());
    }

    private void validateStartTime(LocalDateTime startTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new BeforeCurrentTimeException("설정한 시간이 현재 시간보다 빠릅니다.");
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB("해당 게시물을 찾지 못하였습니다" + postId)
        );
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new EntityNotFoundFromDB("해당 유저를 찾지 못했습니다" + loginId)
        );
    }

}