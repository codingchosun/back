package com.codingchosun.backend.service.evaluation;

import com.codingchosun.backend.domain.Evaluation;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.Template;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.dto.request.EvaluationRequest;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.TemplateNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import com.codingchosun.backend.repository.evaluation.DataJpaEvaluationRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuser.DataJpaPostUserRepository;
import com.codingchosun.backend.repository.template.DataJpaTemplateRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {

    private final DataJpaEvaluationRepository evaluationRepository;
    private final DataJpaUserRepository userRepository;
    private final DataJpaPostRepository postRepository;
    private final DataJpaTemplateRepository templateRepository;
    private final DataJpaPostUserRepository postUserRepository;

    public void saveEvaluation(Long postId, String loginId, List<EvaluationRequest> requests) {
        User fromUser = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        for (EvaluationRequest request : requests) {
            User toUser = findUserByLoginId(request.getToUserLoginId());
            Template template = findTemplateById(request.getTemplateId());

            if (evaluationRepository.existsByPostAndFromUserAndToUser(post, fromUser, toUser)) {
                continue;
            }
            Evaluation evaluation = new Evaluation(template, fromUser, toUser, post);
            evaluationRepository.save(evaluation);

            int updateScore = toUser.calMannerScore(template.getScore());
            log.info("평가 후 매너 점수: {}", updateScore);
        }
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND)
        );
    }

    private Template findTemplateById(Long templateId) {
        return templateRepository.findById(templateId).orElseThrow(
                () -> new TemplateNotFoundFromDB(ErrorCode.TEMPLATE_NOT_FOUND)
        );
    }
}
