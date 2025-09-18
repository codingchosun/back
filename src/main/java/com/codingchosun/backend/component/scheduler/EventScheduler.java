package com.codingchosun.backend.component.scheduler;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Evaluation;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.evaluation.DataJpaEvaluationRepository;
import com.codingchosun.backend.response.UserPairDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventScheduler {

    private final DataJpaPostRepository dataJpaPostRepository;
    private final DataJpaEvaluationRepository dataJpaEvaluationRepository;
    /*
    *   매 30분마다
    *   1. startTime이 지나간 post를 확인해서 post의 참가자를 validate 테이블에 넣어줌
    *   2. startTime이 지나간 post를 확인해서 state를 INACTIVE로 바꿈
    */
    @Scheduled(cron = "0 */5 * * * *")
    public void postEventScheduler(){
        log.info("post event scheduler started at {}", LocalDateTime.now().plusHours(9));
        updateStartTimeEvent();
        updateEndTimeEvent();
    }

    private void updateEndTimeEvent(){
        List<Post> targetPostList = dataJpaPostRepository
                .findAllByStartTimeBeforeAndStateCode(LocalDateTime.now().plusHours(9), StateCode.ACTIVE);

        //확인용
        List<Long> postIdList = targetPostList.stream()
                .map(Post::getPostId)
                .toList();
        log.info("end event post list : {}", postIdList);

        for (Post targetPost : targetPostList) {
            targetPost.setStateCode(StateCode.INACTIVE);
        }
    }

    private void updateStartTimeEvent(){
        List<Post> targetPostList = dataJpaPostRepository
                .findAllByStartTimeBeforeAndStateCode(LocalDateTime.now().plusHours(9), StateCode.ACTIVE);

        //확인용
        List<Long> postIdList = targetPostList.stream()
                .map(Post::getPostId)
                .toList();
        log.info("start event post list : {}", postIdList);

        for (Post targetPost : targetPostList) {
            insertValidate(targetPost);
        }
    }

    private void insertValidate(Post post){
        List<PostUser> postUsers = post.getPostUsers();
        List<UserPairDto> userPairList = UserPairDto
                .makeUserPairList(postUsers.stream().map(PostUser::getUser).toList());

        log.info("userPairList: {}", userPairList);

        for (UserPairDto userPair : userPairList) {
            Evaluation evaluation = new Evaluation();
            evaluation.setPost(post);
            evaluation.setFromUser(userPair.getFromUser());
            evaluation.setToUser(userPair.getToUser());
            Evaluation save = dataJpaEvaluationRepository.save(evaluation);
            log.info("save: {}", save.getEvaluationId());
        }
    }

}
