package com.codingchosun.backend.repository.hashtagrepository;

import com.codingchosun.backend.domain.PostHash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaPostHashRepository extends JpaRepository<PostHash, Long> {
    List<PostHash> findAllByPost_PostId(Long postId);
    List<PostHash> findAllByHashtag_HashtagId(Long hashtagId);
    List<PostHash> findAllByHashtag_HashtagName(String hashtagName);
}