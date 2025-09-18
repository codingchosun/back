package com.codingchosun.backend.service.post;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.invalidrequest.AlreadyJoinedPost;
import com.codingchosun.backend.exception.invalidrequest.NotJoinedPost;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuser.DataJpaPostUserRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.dto.response.UserDTO;
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

    //특정 게시물(모임)의 모든 참가자 목록 조회
    @Transactional(readOnly = true)
    public List<UserDTO> getParticipants(Long postId) {
        Post post = findPostById(postId);

        return post.getPostUsers().stream().map(PostUser::getUser).map(UserDTO::from).toList();
    }

    //로그인된 유저가 특정 게시물(모임) 참여
    public void participatePost(Long postId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        //중복 참가 검사
        if (postUserRepository.existsByUserAndPost(user, post)) {
            throw new AlreadyJoinedPost("이미 모임에 참가한 유저입니다");
        }

        //게시물(모임) 참가(postUser 객체 생성)
        new PostUser(user, post);
    }

    //게시물(모임) 탈퇴
    public void leavePost(Long postId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        PostUser postUser = postUserRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new NotJoinedPost("삭제 대상이 참가자가 아닙니다")
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
                () -> new PostNotFoundFromDB("해당 게시물을 찾지 못하였습니다" + postId)
        );
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new EntityNotFoundFromDB("아이디에 해당하는 유저를 찾지 못했습니다" + loginId)
        );
    }

    private User findUserById(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundFromDB("해당 유저를 찾지 못했습니다" + userId)
        );
    }
}
