package com.codingchosun.backend.repository.postuser;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaPostUserRepository extends JpaRepository<PostUser, Long> {

    Optional<PostUser> findByUserAndPost(User user, Post post);

    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);
}