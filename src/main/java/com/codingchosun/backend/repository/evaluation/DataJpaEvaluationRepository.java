package com.codingchosun.backend.repository.evaluation;

import com.codingchosun.backend.domain.Evaluation;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaEvaluationRepository extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findAllByPostAndFromUser(Post post, User fromUser);

    boolean existsByPostAndFromUserAndToUser(Post post, User fromUser, User toUser);

}
