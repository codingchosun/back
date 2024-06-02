package com.codingchosun.backend.repository.postrepository;


import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DataJpaPostRepository extends JpaRepository<Post, Long>, DataJpaPostRepositoryCustom {
    List<Post> findByTitle(String title);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findAllByStartTimeBeforeAndStateCode(LocalDateTime startTime, StateCode stateCode);

    List<Post> findAllByEndTimeBeforeAndStateCode(LocalDateTime EndTime, StateCode stateCode);
}
