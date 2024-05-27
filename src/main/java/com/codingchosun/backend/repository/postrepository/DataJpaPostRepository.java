package com.codingchosun.backend.repository.postrepository;


import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.response.NoLoginPostsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaPostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}