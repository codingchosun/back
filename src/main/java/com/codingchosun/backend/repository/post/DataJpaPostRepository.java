package com.codingchosun.backend.repository.post;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DataJpaPostRepository extends JpaRepository<Post, Long>, DataJpaPostRepositoryCustom {

    @Query("SELECT p FROM Post p WHERE p.stateCode = 'ACTIVE' ORDER BY p.createdAt DESC")
    Page<Post> findAllActiveByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findAllByEndTimeBeforeAndStateCode(LocalDateTime endTimeBefore, StateCode stateCode);

    @Query(value = "SELECT p FROM Post p JOIN p.postUsers pu WHERE pu.user.loginId = :loginId ORDER BY p.postId DESC")
    List<Post> findParticipatedPosts(@Param("loginId") String loginId);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.postHashes ph WHERE ph.hashtag.hashtagId IN :hashtagIds ORDER BY p.createdAt DESC")
    Page<Post> findPostsByHashTagIdIn(@Param("hashtagIds") List<Long> hashtagIds, Pageable pageable);
}
