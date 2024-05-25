package com.codingchosun.backend.repository.commentrepository;


import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaCommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findAllByPost_PostId(Long postId, Pageable pageable);
    public List<Comment> findAllByUser_UserId(Long userId, Pageable pageable);
}
