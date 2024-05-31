package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.invalidrequest.AlreadyJoinedPost;
import com.codingchosun.backend.exception.invalidrequest.NotJoinedPost;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuserrepository.DataJpaPostUserRepository;
import com.codingchosun.backend.response.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostUserService {

    private final DataJpaPostUserRepository dataJpaPostUserRepository;
    private final DataJpaPostRepository dataJpaPostRepository;

    public Optional<PostUser> getPostUserByUserAndPost(User user, Post post) {
        return dataJpaPostUserRepository.findByUserAndPost(user, post);
    }

    //post의 모든 참가자 가져오기
    public List<UserDTO> getParticipants(Long postId){
        List<PostUser> postUserList = dataJpaPostUserRepository.findAllByPost_PostId(postId);

        List<User> userList = new ArrayList<>();
        for (PostUser postUser : postUserList) {
            userList.add(postUser.getUser());
        }

        return userList.stream().map(UserDTO::new).toList();
    }

    //모임 참가
    public User participate(Long postId, User user) {
        Post post = dataJpaPostRepository.findById(postId).orElseThrow(() -> new RuntimeException("post 못찾음"));

        //중복 참가 검사
        Optional<PostUser> optionalPostUser = dataJpaPostUserRepository.findByUserAndPost(user, post);
        if (optionalPostUser.isPresent()) {
            PostUser postUser = optionalPostUser.get();
            log.warn("post user={},user={},post={}",postUser.getPostUserId(), postUser.getUser().getUserId(), postUser.getPost().getPostId());
            throw new AlreadyJoinedPost("이미 참가한 유저입니다");
        }

        PostUser postUser = new PostUser();
        postUser.setPost(post);
        postUser.setUser(user);
        PostUser save = dataJpaPostUserRepository.save(postUser);
        return save.getUser();
    }

    //모임 탈퇴(삭제 대상과 포스트가 맞는지는 컨트롤러에서 검사할거임)
    public Post leavePost(Post post, User user) {
        Optional<PostUser> optionalPostUser = dataJpaPostUserRepository.findByUserAndPost(user, post);
        if (optionalPostUser.isEmpty()) {
            throw new NotJoinedPost("삭제 대상이 참가자가 아닙니다");
        }
        PostUser postUser = optionalPostUser.get();
        dataJpaPostUserRepository.delete(postUser);

        return post;
    }
}
