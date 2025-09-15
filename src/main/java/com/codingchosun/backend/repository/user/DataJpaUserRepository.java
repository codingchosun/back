package com.codingchosun.backend.repository.user;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface DataJpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdAndPassword(String loginId, String password);

    User findByLoginId(String loginId);

    User findByNickname(String nickName);

    User findByUserId(long id);

    Optional<User> findByNameAndEmail(String name, String email);

    Optional<User> findByNameAndEmailAndLoginId(String name, String email, String loginId);

    User findByStateAndLoginId(StateCode state, String loginId);
}
