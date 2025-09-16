package com.codingchosun.backend.repository.commentrepository;


import com.codingchosun.backend.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaCommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPost_PostId(Long postId, Pageable pageable);

}
