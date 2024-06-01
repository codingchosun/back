package com.codingchosun.backend.repository.userrepository;


import com.codingchosun.backend.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface DataJpaUserRepository extends JpaRepository<User,Long> {
    User findByLoginIdAndPassword(String loginId, String password);
    User findByLoginId(String loginId);
    User findByNickname(String nickName);
    User findByUserId(long id);
    User findByNameAndEmail(String name, String email);
    User findByNameAndEmailAndLoginId(String name, String email, String loginId);
}
