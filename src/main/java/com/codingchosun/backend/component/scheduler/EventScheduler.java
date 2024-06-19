package com.codingchosun.backend.component.scheduler;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.domain.Validate;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.repository.validaterepository.ValidateRepository;
import com.codingchosun.backend.response.UserPairDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventScheduler {

    private final DataJpaPostRepository dataJpaPostRepository;
    private final ValidateRepository validateRepository;
    /*
    *   매 30분마다
    *   1. startTime이 지나간 post를 확인해서 post의 참가자를 validate 테이블에 넣어줌
    *   2. endTime이 지나간 post를 확인해서 state를 INACTIVE로 바꿈
    */
    @Scheduled(cron = "0 0,30 * * * *")
    public void postEventScheduler(){
        log.info("post event scheduler started at {}", LocalDateTime.now());
        updateStartTimeEvent();
        updateEndTimeEvent();
    }

    private void updateEndTimeEvent(){
        List<Post> targetPostList = dataJpaPostRepository
                .findAllByStartTimeBeforeAndStateCode(LocalDateTime.now(), StateCode.ACTIVE);

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
                .findAllByStartTimeBeforeAndStateCode(LocalDateTime.now(), StateCode.ACTIVE);

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
            Validate validate = new Validate();
            validate.setPost(post);
            validate.setFromUser(userPair.getFromUser());
            validate.setToUser(userPair.getToUser());
            Validate save = validateRepository.save(validate);
            log.info("save: {}", save.getValidateId());
        }
    }

}
