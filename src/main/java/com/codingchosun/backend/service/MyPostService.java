package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.postuserrepository.DataJpaPostUserRepository;
import com.codingchosun.backend.response.MyPostResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MyPostService {

    private final DataJpaPostUserRepository postUserRepository;
    public MyPostService(DataJpaPostUserRepository postUserRepository) {this.postUserRepository = postUserRepository;}

    public List<MyPostResponse> getMyPost(User user)  {

        List<PostUser> postUsers = postUserRepository.findAllByUser(user);
        log.info(postUsers.toString());
        List<MyPostResponse> myPostResponses = new ArrayList<>();

        for (PostUser postUser : postUsers) {
            Post post = postUser.getPost();
            Long id = postUser.getPost().getPostId();
            myPostResponses.add(MyPostResponse.builder()
                    .postId(id)
                    .title(post.getTitle())
                    .createAt(post.getCreatedAt())
                    .author(post.getUser().getNickname())
                    .build());
        }
        return myPostResponses;
    }
}
