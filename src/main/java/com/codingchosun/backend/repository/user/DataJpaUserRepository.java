package com.codingchosun.backend.repository.user;

import com.codingchosun.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdAndPassword(String loginId, String password);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByUserId(long id);

    Optional<User> findByNameAndEmail(String name, String email);

    Optional<User> findByNameAndEmailAndLoginId(String name, String email, String loginId);

}
