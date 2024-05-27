package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.postuserrepository.PostUserRepository;
import com.codingchosun.backend.response.MyPostResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyPostService {

    private final PostUserRepository postUserRepository;
    public MyPostService(PostUserRepository postUserRepository) {this.postUserRepository = postUserRepository;}

    public List<MyPostResponse> getMyPost(User user)  {

        List<PostUser> postUsers = postUserRepository.findAllByUser(user);
        List<MyPostResponse> myPostResponses = new ArrayList<>();

        for (PostUser postUser : postUsers) {
            Post post = postUser.getPost();
            myPostResponses.add(MyPostResponse.builder()
                    .title(post.getTitle())
                    .createAt(post.getCreatedAt())
                    .author(post.getUser().getNickname())
                    .build());
        }
        return myPostResponses;
    }
}
