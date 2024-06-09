package com.codingchosun.backend.repository.postrepository;


import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DataJpaPostRepository extends JpaRepository<Post, Long>, DataJpaPostRepositoryCustom {
    List<Post> findByTitle(String title);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.stateCode = 'ACTIVE' ORDER BY p.createdAt DESC")
    Page<Post> findAllActiveByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findAllByStartTimeBeforeAndStateCode(LocalDateTime startTime, StateCode stateCode);

    List<Post> findAllByEndTimeBeforeAndStateCode(LocalDateTime EndTime, StateCode stateCode);

    Page<Post> findAllByTitleContainingAndStateCode(String title, Pageable pageable, StateCode stateCode);

    List<Post> findAllByStateCodeAndTitleContaining(StateCode stateCode,String title);

    Page<Post> findAllByStateCode(Pageable pageable, StateCode stateCode);

}
