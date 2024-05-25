package com.codingchosun.backend.repository.imagerepository;

import com.codingchosun.backend.domain.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DataJpaImageRepository extends JpaRepository<Image, Long> {
    Slice<Image> findByPost_PostId(Long postId, Pageable pageable);    //slice는 더보기 형태의 페이지
}
