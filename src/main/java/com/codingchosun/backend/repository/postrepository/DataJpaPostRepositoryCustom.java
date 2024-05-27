package com.codingchosun.backend.repository.postrepository;

import com.codingchosun.backend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DataJpaPostRepositoryCustom {
    Page<Post> findPostsByHashTagId(List<Long> hashTagId, Pageable pageable);
}
