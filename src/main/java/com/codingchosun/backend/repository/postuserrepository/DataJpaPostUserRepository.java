package com.codingchosun.backend.repository.postuserrepository;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaPostUserRepository extends JpaRepository<PostUser, Long> {
    List<PostUser> findAllByPost_PostId(Long postId);
    List<PostUser> findAllByUser_UserId(Long userId);

    //모임 탈퇴 만들때 쓰려고 만듦
    PostUser findByUserAndPost(User user, Post post);
}
