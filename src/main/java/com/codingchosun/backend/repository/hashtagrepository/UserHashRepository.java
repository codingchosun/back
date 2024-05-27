package com.codingchosun.backend.repository.hashtagrepository;


import com.codingchosun.backend.domain.UserHash;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface UserHashRepository extends JpaRepository<UserHash, Long> {
    List<UserHash> findHashtagsByUser_UserId(Long userId);
    UserHash findHashtagsByHashtag_HashtagIdAndUser_UserId(Long hashtagId, Long userId);
}