package com.codingchosun.backend.service;


import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.*;
import com.codingchosun.backend.exception.ObjectNotFound;
import com.codingchosun.backend.exception.invalidrequest.InvalidEditorException;
import com.codingchosun.backend.exception.invalidrequest.IsNotPostHash;
import com.codingchosun.backend.exception.invalidtime.TimeBeforeCurrentException;
import com.codingchosun.backend.exception.notfoundfromdb.HashtagNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaPostHashRepository;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaUserHashRepository;
import com.codingchosun.backend.repository.imagerepository.DataJpaImageRepository;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuserrepository.DataJpaPostUserRepository;
import com.codingchosun.backend.request.PostUpdateRequest;
import com.codingchosun.backend.request.RegisterPostRequest;
import com.codingchosun.backend.request.ResearchRequest;
import com.codingchosun.backend.response.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final DataJpaImageRepository dataJpaImageRepository;
    private final ValidateService validateService;
    private final DataJpaUserHashRepository dataJpaUserHashRepository;


    //post자체가 필요한 경우
    public Optional<Post> getPost(Long postId){
        return dataJpaPostRepository.findById(postId);
    }

    //작성한 모임글의 내용만 가져오기
    public PostResponse getPostResponse(Long postId) {

        Post post = dataJpaPostRepository.findById(postId)
                .orElseThrow( () -> new PostNotFoundFromDB("postId: " + postId + "를 찾지 못했습니다"));

        //post의 조회수 증가
        post.increaseViewCount();

        return new PostResponse(post);
    }

    public Post registerPost(RegisterPostRequest registerPostRequest, User user) {

        //post만들고 영속하는 과정
        Post post = new Post();
        post.setUser(user);
        post.setTitle(registerPostRequest.getTitle());
        post.setContent(registerPostRequest.getContent());
        post.setStateCode(StateCode.ACTIVE);
        post.setViewCount(0L);

        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        //약속시간이 현재 시간보다 늦은지 확인
        if( registerPostRequest.getStartTime().isBefore(now) ){
            throw new TimeBeforeCurrentException("현재 시간: " + now + "설정한 시간: " +registerPostRequest.getStartTime());
        }
        post.setStartTime(registerPostRequest.getStartTime());
        post.setEndTime(registerPostRequest.getStartTime().plusDays(1));

        Post save = dataJpaPostRepository.save(post);

        //작성자는 참여자이기도 하므로 참여인원에 등록
        PostUser postUser = new PostUser();
        postUser.setUser(user);
        postUser.setPost(post);
        dataJpaPostUserRepository.save(postUser);


        //PostHash에 등록하는 과정
        List<String> hashtagStrings = registerPostRequest.getHashtags();
        for (String hashtagString : hashtagStrings) {
            Hashtag hashtag;

            Optional<Hashtag> optionalHashtag = dataJpaHashtagRepository.findByHashtagName(hashtagString);
            if(optionalHashtag.isPresent()){    //findby해서 있으면 가져오고
                hashtag = optionalHashtag.get();
            }
            else {  //없으면 새 해쉬태그 만들기
                Hashtag newHashtag = new Hashtag();
                newHashtag.setHashtagName(hashtagString);
                hashtag = dataJpaHashtagRepository.save(newHashtag);
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


    public NoLoginPostsHashtagsResponse noLoginGetPosts(Pageable pageable) {
        List<Hashtag> hashtagList = dataJpaHashtagRepository.findRandomHashtags(5);
        List<HashtagDto> hashtagDtoList = hashtagList.stream().map(HashtagDto::new).toList();
        Page<Post> posts = dataJpaPostRepository.findAllByOrderByCreatedAtDesc(pageable);
//        String[] split = dataJpaImageRepository.findFirstByPost(m).orElse(new Image()).getUrl().split("/");

        Page<NoLoginPostsResponse> noLoginPostsResponses = posts.map(
                m -> new NoLoginPostsResponse().builder()
                                                .id(m.getPostId())
                                                .contents(m.getContent())
                                                .path(splitImagePath(dataJpaImageRepository.findFirstByPost(m).orElse(new Image()).getUrl()))
                                                .title(m.getTitle())
                                                .build());

        return new NoLoginPostsHashtagsResponse().builder()
                .noLoginPostsResponses(noLoginPostsResponses)
                .hashtagDtoList(hashtagDtoList)
                .build();
    }

    public String splitImagePath(String path) {
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    //ToDo 해시태그 값이 널이면 최신 포스트 보내주자
    public LoginPostsHashtagResponse loginPostsRequests(User user, Pageable pageable) {
        List<UserHash> userHashList = dataJpaUserHashRepository.findHashtagsByUser_UserId(user.getUserId());
        List<Hashtag> hashtagList = new ArrayList<>();
        List<Long> hashIds = new ArrayList<>();
        for (UserHash userHash : userHashList) {
            hashtagList.add(userHash.getHashtag());
            hashIds.add(userHash.getHashtag().getHashtagId());
        }

        List<HashtagDto> hashtagDtoList = hashtagList.stream().map(HashtagDto::new).toList();

        Page<Post> postPage = dataJpaPostRepository.findPostsByHashTagId(hashIds, pageable);
        Page<LoginPostsResponse> loginPostsRequests = postPage.map(
                m -> new LoginPostsResponse().builder()
                        .id(m.getPostId())
                        .contents(m.getContent())
                        .path(splitImagePath(dataJpaImageRepository.findFirstByPost(m).orElse(new Image()).getUrl()))
                        .title(m.getTitle())
                        .build());

        return new LoginPostsHashtagResponse().builder()
                .loginPostsResponses(loginPostsRequests)
                .hashtagDtoList(hashtagDtoList)
                .build();

    }

    public Page<ResearchPostResponse> researchPost(ResearchRequest researchQuery, Pageable pageable) {
        List<String> titleQuery = new ArrayList<>();
        List<String> hashQuery = new ArrayList<>();

        String query = researchQuery.getResearchQuery();
        String[] querys = query.split(" ");

        for (String q : querys) {
            if (q.contains("#")) {
                hashQuery.add(q);
            } else {
                titleQuery.add(q);
            }
        }

        Page<Post> posts = dataJpaPostRepository.findPostsByResearchQuery(titleQuery, hashQuery, pageable);
        return posts.map(
                m -> new ResearchPostResponse().builder()
                        .id(m.getPostId())
                        .title(m.getTitle())
                        .contents(m.getContent())
                        .path(dataJpaImageRepository.findFirstByPost(m).orElse(new Image()).getUrl())
                        .build()
        );
    }



    public Post editPost(Long postId, User user, PostUpdateRequest postUpdateRequest){
        Post post = dataJpaPostRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("postId: " + postId + "를 찾지 못해 글을 수정할수없음"));

        log.info("작성자 : {}, 수정자 : {}", post.getUser().getUserId(), user.getUserId());

        if( !(post.getUser().getUserId().equals(user.getUserId())) ){
            throw new InvalidEditorException("작성자와 수정자 불일치 작성자: " + post.getUser().getUserId() + " 수정자: " + user.getUserId() );
        }

        //글 수정

        //기본정보 수정
        post.setTitle(postUpdateRequest.getTitle());
        post.setContent(postUpdateRequest.getContent());

        //시간 수정
        post.setStartTime(postUpdateRequest.getStartTime());
        post.setEndTime(postUpdateRequest.getStartTime().plusDays(1));

        //해쉬태그 수정
            //삭제
        deleteHashtagFromPost(postUpdateRequest, post);
            //추가
        addHashtagToPost(postUpdateRequest, post);

        //이미지 수정
        for (Long removeImage : postUpdateRequest.getRemoveImages()) {
            dataJpaImageRepository.deleteById(removeImage);
        }

        return post;
    }

















//기타 메서드들


    private void addHashtagToPost(PostUpdateRequest postUpdateRequest, Post post) {
        for (String addTag : postUpdateRequest.getAddTags()) {
            Optional<Hashtag> optionalHashtag = dataJpaHashtagRepository.findByHashtagName(addTag);

            if( optionalHashtag.isPresent() ){  //이미 있는 해쉬태그의 경우
                Hashtag hashtag = optionalHashtag.get();

                PostHash postHash = new PostHash();
                postHash.setPost(post);
                postHash.setHashtag(hashtag);
                dataJpaPostHashRepository.save(postHash);
            }
            else {
                //해쉬태그 만들기
                Hashtag hashtag = new Hashtag();
                hashtag.setHashtagName(addTag);
                Hashtag savedHashtag = dataJpaHashtagRepository.save(hashtag);

                //해쉬태그 저장
                PostHash postHash = new PostHash();
                postHash.setPost(post);
                postHash.setHashtag(savedHashtag);
                dataJpaPostHashRepository.save(postHash);
            }

        }
    }

    private void deleteHashtagFromPost(PostUpdateRequest postUpdateRequest, Post post) {
        for (String removeTag : postUpdateRequest.getRemoveTags()) {
            Hashtag hashtag = dataJpaHashtagRepository.findByHashtagName(removeTag).orElseThrow(
                    () -> new HashtagNotFoundFromDB("없는 해쉬태그"));
            PostHash postHash = dataJpaPostHashRepository.findByPostAndHashtag(post, hashtag)
                    .orElseThrow(() -> new IsNotPostHash("이 글에 달린 태그가 아님"));

            dataJpaPostHashRepository.delete(postHash); //태그 지우기
        }
    }


}
