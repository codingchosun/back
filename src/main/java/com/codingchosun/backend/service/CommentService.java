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

    public String deleteComment(User user, Long postId, Long commentId) {
        Comment comment = dataJpaCommentRepository.findCommentByPost_PostIdAndCommentId(postId, commentId);
        if (comment.getUser().getUserId() != user.getUserId()) {
            return "댓글 작성자가 아닙니다.";
        }
        int count = dataJpaCommentRepository.deleteCommentByPost_PostIdAndCommentId(postId, commentId);

        if (count == 0) {
            return "포스트아이디와 댓글아이디에 일치하는 댓글이 없습니다.";
        }
        return "댓글 " + count + "개가 삭제됐습니다.";
    }

    public Page<CommentResponse> getPagedComments(Pageable pageable, Long postId){
        return dataJpaCommentRepository.findAllByPost_PostId(postId, pageable).map(CommentResponse::new);
    }
}
