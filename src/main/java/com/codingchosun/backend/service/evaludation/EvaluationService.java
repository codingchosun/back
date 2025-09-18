package com.codingchosun.backend.service.evaludation;


import com.codingchosun.backend.domain.Evaluation;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.Template;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.evaluation.DataJpaEvaluationRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.postuser.DataJpaPostUserRepository;
import com.codingchosun.backend.repository.template.DataJpaTemplateRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.dto.request.EvaluationRequest;
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

    //점수 평가
    public void saveEvaluation(Long postId, String loginId, List<EvaluationRequest> requests) {
        User fromUser = findUserByLoginId(loginId);
        Post post = findPostById(postId);

        for (EvaluationRequest request : requests) {
            User toUser = findUserById(request.getToUserId());
            Template template = findTemplateById(request.getTemplateId());

            //중복 평가 방지
            if (evaluationRepository.existsByPostAndFromUserAndToUser(post, fromUser, toUser)) {
                continue;
            }
            //평가 저장
            Evaluation evaluation = new Evaluation(template, fromUser, toUser, post);
            evaluationRepository.save(evaluation);

            //매너 점수 갱신
            int updateScore = toUser.calMannerScore(template.getScore());
            log.info("평가 후 매너 점수: {}", updateScore);
        }
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundFromDB("사용자를 찾을 수 없습니다: " + loginId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundFromDB("평가 대상을 찾을 수 없습니다: " + userId));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("게시물을 찾을 수 없습니다: " + postId));
    }

    private Template findTemplateById(Long templateId) {
        return templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundFromDB("템플릿을 찾을 수 없습니다: " + templateId));
    }
}
