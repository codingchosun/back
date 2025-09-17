package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.invalidrequest.InvalidEditorException;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.comment.DataJpaCommentRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.request.RegisterCommentRequest;
import com.codingchosun.backend.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final DataJpaCommentRepository dataJpaCommentRepository;
    private final DataJpaUserRepository dataJpaUserRepository;
    private final DataJpaPostRepository dataJpaPostRepository;

    public Comment registerComments(Long postId, String loginId, RegisterCommentRequest registerCommentRequest) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);
        Comment comment = new Comment(registerCommentRequest.getContents(), user, post);

        return dataJpaCommentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String loginId) {
        User user = findUserByLoginId(loginId);
        Comment comment = findCommentById(commentId);

        if (!Objects.equals(comment.getUser().getUserId(), user.getUserId())) {
            throw new InvalidEditorException("댓글 작성자가 아닙니다: " + loginId);
        }

        comment.validateOwner(user);
        dataJpaCommentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getPagedComments(Pageable pageable, Long postId) {
        return dataJpaCommentRepository.findAllByPost_PostId(postId, pageable)
                .map(CommentResponse::from);
    }

    private User findUserByLoginId(String loginId) {
        return dataJpaUserRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundFromDB("사용자를 찾을 수 없습니다: " + loginId));
    }

    private Post findPostById(Long postId) {
        return dataJpaPostRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("게시물을 찾을 수 없습니다: " + postId));
    }

    private Comment findCommentById(Long commentId) {
        return dataJpaCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundFromDB("댓글을 찾을 수 없습니다: " + commentId));
    }
}
