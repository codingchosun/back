package com.codingchosun.backend.service.post;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.dto.response.UserDTO;
import com.codingchosun.backend.exception.common.ErrorCode;
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

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostParticipantService {

    private final DataJpaPostUserRepository postUserRepository;
    private final DataJpaPostRepository postRepository;
    private final DataJpaUserRepository userRepository;

    //로그인된 유저가 특정 게시물(모임) 참여
    public void participatePost(Long postId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        if (postUserRepository.existsByUserAndPost(user, post)) {
            throw new UserAlreadyParticipantException(ErrorCode.USER_ALREADY_PARTICIPANT);
        }

        new PostUser(user, post);
    }

    //게시물(모임) 탈퇴
    public void leavePost(Long postId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        PostUser postUser = postUserRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new UserNotParticipantException(ErrorCode.USER_NOT_PARTICIPANT)
        );

        postUserRepository.delete(postUser);
    }

    //게시물(모임) 작성자가 참가자 추방
    public void banishPost(Long postId, Long banishUserId, String hostLoginId) {
        User host = findUserByLoginId(hostLoginId);
        User banishUser = findUserById(banishUserId);
        Post post = findPostById(postId);

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

    private User findUserById(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
    }
}
