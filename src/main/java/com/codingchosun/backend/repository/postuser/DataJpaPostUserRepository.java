package com.codingchosun.backend.repository.postuser;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DataJpaPostUserRepository extends JpaRepository<PostUser, Long> {

    List<PostUser> findAllByPost_PostId(Long postId);

    List<PostUser> findAllByUser_UserId(Long userId);

    List<PostUser> findAllByUser(User user);

    Optional<PostUser> findByUserAndPost(User user, Post post);

    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);
}