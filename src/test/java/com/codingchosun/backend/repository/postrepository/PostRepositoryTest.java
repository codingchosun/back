package com.codingchosun.backend.repository.postrepository;

import com.codingchosun.backend.domain.Post;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void postSaveTest() {
        Post post = new Post();
        post.setTitle("Post Title");
        post.setContent("Post Content");

        log.info("post: {}", post);
        Post save = postRepository.save(post);
        log.info("save: {}", save);

        Assertions.assertThat(post).isEqualTo(save);
    }
}