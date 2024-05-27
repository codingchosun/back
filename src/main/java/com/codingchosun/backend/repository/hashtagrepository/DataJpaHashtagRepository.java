package com.codingchosun.backend.repository.hashtagrepository;


import com.codingchosun.backend.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DataJpaHashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findByHashtagIdIn(List<Long> hashtagIds);
    Optional<Hashtag> findByHashtagName(String hashtagName);
    @Query(value = "SELECT * FROM hashtag ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Hashtag> findRandomHashtags(@Param("limit") int limit);
}
