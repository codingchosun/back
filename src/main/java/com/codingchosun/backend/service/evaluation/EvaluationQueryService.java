package com.codingchosun.backend.service.evaluation;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.dto.response.EvaluationResponse;
import com.codingchosun.backend.dto.response.TemplateDto;
import com.codingchosun.backend.dto.response.UserToEvaluateDto;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.evaluation.DataJpaEvaluationRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.template.DataJpaTemplateRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationQueryService {

    private final DataJpaEvaluationRepository evaluationRepository;
    private final DataJpaUserRepository userRepository;
    private final DataJpaPostRepository postRepository;
    private final DataJpaTemplateRepository templateRepository;

    public EvaluationResponse getTargetsToEvaluate(Long postId, String loginId) {
        User currentUser = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        List<Long> evaluatedUserIds = evaluationRepository.findAllByPostAndFromUser(post, currentUser).stream()
                .map(evaluation -> evaluation.getToUser().getUserId())
                .toList();

        List<UserToEvaluateDto> usersToEvaluate = post.getPostUsers().stream()
                .map(PostUser::getUser)
                .filter(user -> !user.getUserId().equals(currentUser.getUserId()) && !evaluatedUserIds.contains(user.getUserId()))
                .map(UserToEvaluateDto::from)
                .toList();

        List<TemplateDto> templates = templateRepository.findAll().stream()
                .map(TemplateDto::from)
                .toList();

        return new EvaluationResponse(usersToEvaluate, templates);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND)
        );
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
    }
}
