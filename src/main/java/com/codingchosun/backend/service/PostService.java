package com.codingchosun.backend.service;


import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.*;
import com.codingchosun.backend.exception.EntityNotFoundFromDB;
import com.codingchosun.backend.exception.HashtagNotFoundFromDB;
import com.codingchosun.backend.exception.PostNotFoundFromDB;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaPostHashRepository;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuserrepository.DataJpaPostUserRepository;
import com.codingchosun.backend.request.RegisterPostRequest;
import com.codingchosun.backend.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final DataJpaPostRepository dataJpaPostRepository;
    private final DataJpaPostUserRepository dataJpaPostUserRepository;
    private final DataJpaHashtagRepository dataJpaHashtagRepository;
    private final DataJpaPostHashRepository dataJpaPostHashRepository;
    private final ValidateService validateService;

    public PostResponse findById(Long postId) {

        Post post = dataJpaPostRepository.findById(postId)
                .orElseThrow( () -> new PostNotFoundFromDB("postId: " + postId + "를 찾지 못했습니다"));


        return new PostResponse(post);
    }

    public Post registerPost(RegisterPostRequest registerPostRequest, User user) {

        //post만들고 영속하는 과정
        Post post = new Post();
        post.setUser(user);
        post.setTitle(registerPostRequest.getTitle());
        post.setContent(registerPostRequest.getContent());
        post.setStateCode(StateCode.ACTIVE);

        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setStartTime(now);
        post.setEndTime(now.plusDays(1));
        post.setViewCount(0L);
        Post save = dataJpaPostRepository.save(post);

        //작성자는 참여자이기도 하므로 참여인원에 등록
        PostUser postUser = new PostUser();
        postUser.setUser(user);
        postUser.setPost(post);
        dataJpaPostUserRepository.save(postUser);

        //validate에 등록하는 과정
        //근데 이거를 postService에서 하는게 맞는지는 모르겠음 validate의 서비스계층에서 하는게 맞는거 같기도함
        //그렇다고 validateService를 불러서쓰자니 서비스계층이 서비스계층을 호출하는것도 아닌것같음
//        validateService.saveValidate2(save.getPostId(), user.getUserId());
        //validate테이블은 모임인원이 확정됐을때 등록해야할것 같음 여기서 넣어버리면 toUser가 null인게 들어감

        //PostHash에 등록하는 과정
        List<String> hashtagStrings = registerPostRequest.getHashtags();
        for (String hashtagString : hashtagStrings) {
            Hashtag hashtag;
            try {
                hashtag = dataJpaHashtagRepository.findByHashtagName(hashtagString);
                log.info("hash {}", hashtag.getHashtagName());
            }catch (NoSuchElementException e){
                throw new HashtagNotFoundFromDB(hashtagString, e);
            }
            PostHash postHash = new PostHash();
            postHash.setPost(save);
            postHash.setHashtag(hashtag);
            dataJpaPostHashRepository.save(postHash);
        }

        return save;
    }

    //검증 필요 + state_code가 active인것만 검색되게 할지 결정해야함
    public List<PostResponse> findByTitle(String title) {
        List<Post> posts = dataJpaPostRepository.findByTitle(title);
        return posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
