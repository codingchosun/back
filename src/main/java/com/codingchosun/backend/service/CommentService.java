package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.commentrepository.DataJpaCommentRepository;
import com.codingchosun.backend.request.RegisterCommentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final DataJpaCommentRepository dataJpaCommentRepository;

    public Comment registerComments(User user, Post post, RegisterCommentRequest registerCommentRequest){
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(registerCommentRequest.getContents());
        comment.setCreatedAt(LocalDateTime.now());

        return dataJpaCommentRepository.save(comment);
    }
}
