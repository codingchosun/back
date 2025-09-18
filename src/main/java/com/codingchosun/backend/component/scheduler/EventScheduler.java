package com.codingchosun.backend.component.scheduler;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.repository.evaluation.DataJpaEvaluationRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class EventScheduler {

    private final DataJpaPostRepository postRepository;
    private final DataJpaEvaluationRepository evaluationRepository;

    //매 (5)분 마다 실행되어 종료 시간을 지난 게시물(모임)을 비활성화
    @Scheduled(cron = "0 */5 * * * *")
    public void postEventScheduler() {
        LocalDateTime now = LocalDateTime.now(); //서버 시간 주의

        List<Post> expiredPosts = postRepository.findAllByEndTimeBeforeAndStateCode(now, StateCode.ACTIVE);

        if (expiredPosts.isEmpty()) {
            log.info("만료된 모임이 없습니다");
            return;
        }

        for (Post post : expiredPosts) {
            log.info("게시물 {} 종료 시간: {}", post.getPostId(), post.getEndTime());
            post.deletePost();
        }

        log.info("총 {}개 모임 상태 업데이트 완료.", expiredPosts.size());
    }

}
