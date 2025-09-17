package com.codingchosun.backend.repository.validate;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.domain.Validate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidateRepository extends JpaRepository<Validate, Long> {
    Optional<Validate> findByPostAndAndFromUserAndToUser(Post post, User fromUser, User toUser);
}
