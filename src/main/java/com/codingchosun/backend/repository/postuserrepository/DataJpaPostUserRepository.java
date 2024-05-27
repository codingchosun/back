package com.codingchosun.backend.repository.postuserrepository;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Transactional
@Repository
public interface DataJpaPostUserRepository extends JpaRepository<PostUser, Long> {
    List<PostUser> findAllByPost_PostId(Long postId);
    List<PostUser> findAllByUser_UserId(Long userId);

    List<PostUser> findAllByUser(User user);

    //모임 탈퇴 만들때 쓰려고 만듦
    PostUser findByUserAndPost(User user, Post post);
}