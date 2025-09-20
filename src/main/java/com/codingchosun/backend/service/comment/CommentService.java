package com.codingchosun.backend.service.comment;

import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.dto.request.RegisterCommentRequest;
import com.codingchosun.backend.dto.response.CommentRegistrationResponse;
import com.codingchosun.backend.dto.response.CommentResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.invalidrequest.UnauthorizedActionException;
import com.codingchosun.backend.exception.notfoundfromdb.CommentNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.comment.DataJpaCommentRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
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

    public CommentRegistrationResponse registerComments(Long postId, String loginId, RegisterCommentRequest registerCommentRequest) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);
        Comment comment = new Comment(registerCommentRequest.getContents(), user, post);

        dataJpaCommentRepository.save(comment);

        return new CommentRegistrationResponse(comment.getCommentId());
    }

    public void deleteComment(Long postId, Long commentId, String loginId) {
        User user = findUserByLoginId(loginId);
        Comment comment = findCommentById(commentId);

        if (!Objects.equals(comment.getPost().getPostId(), postId)) {
            throw new UnauthorizedActionException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        if (!Objects.equals(comment.getUser().getUserId(), user.getUserId())) {
            throw new UnauthorizedActionException(ErrorCode.UNAUTHORIZED_ACTION);
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
                .orElseThrow(
                        () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
                );
    }

    private Post findPostById(Long postId) {
        return dataJpaPostRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND)
                );
    }

    private Comment findCommentById(Long commentId) {
        return dataJpaCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundFromDB(ErrorCode.COMMENT_NOT_FOUND)
                );
    }
}
