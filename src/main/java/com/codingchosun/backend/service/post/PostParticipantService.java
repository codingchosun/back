package com.codingchosun.backend.service.post;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.invalidrequest.UnauthorizedActionException;
import com.codingchosun.backend.exception.invalidrequest.UserAlreadyParticipantException;
import com.codingchosun.backend.exception.invalidrequest.UserNotParticipantException;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuser.DataJpaPostUserRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostParticipantService {

    private final DataJpaPostUserRepository postUserRepository;
    private final DataJpaPostRepository postRepository;
    private final DataJpaUserRepository userRepository;

    public void participatePost(Long postId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        if (postUserRepository.existsByUserAndPost(user, post)) {
            throw new UserAlreadyParticipantException(ErrorCode.USER_ALREADY_PARTICIPANT);
        }

        PostUser postUser = new PostUser(user, post);
        post.getPostUsers().add(postUser);
        user.getPostUsers().add(postUser);
        postUserRepository.save(postUser);
    }

    public void leavePost(Long postId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        PostUser postUser = postUserRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new UserNotParticipantException(ErrorCode.USER_NOT_PARTICIPANT)
        );

        postUserRepository.delete(postUser);
    }

    public void banishPost(Long postId, String banishUserLoginId, String hostLoginId) {
        User host = findUserByLoginId(hostLoginId);
        User banishUser = findUserByLoginId(banishUserLoginId);

        Post post = findPostById(postId);
        if (!post.getUser().getLoginId().equals(hostLoginId)) {
            throw new UnauthorizedActionException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        post.validateOwner(host);

        postUserRepository.deleteByUserAndPost(banishUser, post);
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
