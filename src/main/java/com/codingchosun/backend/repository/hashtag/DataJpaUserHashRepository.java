package com.codingchosun.backend.repository.hashtag;

import com.codingchosun.backend.domain.UserHash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataJpaUserHashRepository extends JpaRepository<UserHash, Long> {

    List<UserHash> findHashtagsByUser_UserId(Long userId);

    @Query("SELECT uh.hashtag.hashtagId FROM UserHash uh WHERE uh.user.loginId = :loginId")
    List<Long> findHashtagIdsByLoginId(@Param("loginId") String loginId);

}