package com.codingchosun.backend.repository.postrepository;


import com.codingchosun.backend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataJpaPostRepository extends JpaRepository<Post, Long>, DataJpaPostRepositoryCustom{
    List<Post> findByTitle(String title);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
