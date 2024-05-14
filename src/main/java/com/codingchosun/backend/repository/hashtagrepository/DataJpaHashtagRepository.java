package com.codingchosun.backend.repository.hashtagrepository;


import com.codingchosun.backend.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaHashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findByHashtagIdIn(List<Long> hashtagIds);
}
