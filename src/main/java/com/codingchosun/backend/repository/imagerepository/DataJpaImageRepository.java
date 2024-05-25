package com.codingchosun.backend.repository.imagerepository;

import com.codingchosun.backend.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaImageRepository extends JpaRepository<Image, Long> {
    Page<Image> findByPost_PostId(Long postId, Pageable pageable);
}
