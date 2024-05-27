package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.commentrepository.DataJpaCommentRepository;
import com.codingchosun.backend.request.RegisterCommentRequest;
import com.codingchosun.backend.response.CommentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Page<CommentResponse> getPagedComments(Pageable pageable, Long postId){
        return dataJpaCommentRepository.findAllByPost_PostId(postId, pageable).map(CommentResponse::new);
    }
}
