package com.codingchosun.backend.service.post;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.dto.response.MyPostResponse;
import com.codingchosun.backend.dto.response.UserDTO;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryService {

    private final DataJpaPostRepository postRepository;
    private final DataJpaUserRepository userRepository;

    public List<MyPostResponse> getMyPosts(String loginId) {
        List<Post> participatedPosts = postRepository.findParticipatedPosts(loginId);

        if (participatedPosts.isEmpty()) {
            throw new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND);
        }

        log.info("게시물 목록: {}", participatedPosts);

        return participatedPosts.stream()
                .map(MyPostResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getParticipants(Long postId) {
        Post post = findPostById(postId);

        return post.getPostUsers().stream().map(PostUser::getUser).map(UserDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public List<MyPostResponse> getMyParticipatedPosts(String loginId) {
        User user = findUserByLoginId(loginId);

        return user.getPostUsers().stream()
                .map(PostUser::getPost)
                .map(MyPostResponse::from)
                .collect(Collectors.toList());
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND)
        );
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
    }
}
