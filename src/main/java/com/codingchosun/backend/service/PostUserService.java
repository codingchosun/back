package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.postrepository.PostRepository;
import com.codingchosun.backend.repository.postuserrepository.PostUserRepository;
import com.codingchosun.backend.response.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostUserService {

    private final PostUserRepository postUserRepository;
    private final PostRepository postRepository;

    //post의 모든 참가자 가져오기
    public List<UserDTO> getParticipants(Long postId){
        List<PostUser> postUserList = postUserRepository.findAllByPost_PostId(postId);

        List<User> userList = new ArrayList<>();
        for (PostUser postUser : postUserList) {
            userList.add(postUser.getUser());
        }

        return userList.stream().map(UserDTO::new).toList();
    }

    //모임 참가
    public User participate(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post 못찾음"));
        PostUser postUser = new PostUser();
        postUser.setPost(post);
        postUser.setUser(user);
        PostUser save = postUserRepository.save(postUser);
        return save.getUser();
    }
}
