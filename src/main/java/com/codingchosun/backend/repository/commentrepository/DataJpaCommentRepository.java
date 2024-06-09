package com.codingchosun.backend.repository.commentrepository;


import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataJpaCommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPost_PostId(Long postId, Pageable pageable);
    List<Comment> findAllByUser_UserId(Long userId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.post.postId = :postId AND c.content = :commentId")
    int deleteCommentByPost_PostIdAndCommentId(@Param("postId") Long postId, @Param("commentId") Long commentId);
//    Comment deleteCommentByPost_PostIdAndCommentId(Long postId, Long commentId);
}
