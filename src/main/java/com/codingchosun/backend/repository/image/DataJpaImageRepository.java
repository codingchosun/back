package com.codingchosun.backend.repository.image;

import com.codingchosun.backend.domain.Image;
import com.codingchosun.backend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaImageRepository extends JpaRepository<Image, Long> {
    Page<Image> findByPost_PostId(Long postId, Pageable pageable);

    Optional<Image> findByPost(Post post);
    Optional<Image> findFirstByPost(Post post);
}
