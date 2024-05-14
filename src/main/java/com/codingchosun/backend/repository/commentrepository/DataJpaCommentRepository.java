package com.codingchosun.backend.repository.commentrepository;


import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaCommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPost(Post post);
    public List<Comment> findByUser(User user);
}
