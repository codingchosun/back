package com.codingchosun.backend.repository.postrepository;


import com.codingchosun.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaPostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);
}
