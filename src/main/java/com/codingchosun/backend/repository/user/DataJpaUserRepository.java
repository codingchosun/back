package com.codingchosun.backend.repository.user;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.User;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface DataJpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdAndPassword(String loginId, String password);

    Optional<User> findByLoginId(String loginId);

    User findByNickname(String nickName);

    Optional<User> findByUserId(long id);

    Optional<User> findByNameAndEmail(String name, String email);

    Optional<User> findByNameAndEmailAndLoginId(String name, String email, String loginId);

    Optional<User> findByStateCodeAndLoginId(StateCode state, String loginId);

    User getUserByLoginId(@NotNull @Size(min = 6, max = 16) String loginId);
}
