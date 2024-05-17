package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.*;
import com.codingchosun.backend.exception.ObjectNotFound;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.repository.templaterepository.TemplateRepository;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.repository.validaterepository.ValidateRepository;
import com.codingchosun.backend.request.UserValidate;
import com.codingchosun.backend.request.ValidateRequest;
import com.codingchosun.backend.response.MembersAndTemplates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ValidateService {
    private final ValidateRepository validateRepository;
    private final DataJpaUserRepository dataJpaUserRepository;
    private final DataJpaPostRepository dataJpaPostRepository;
    private final TemplateRepository templateRepository;

    // 평가하기 했을 때 저장되는 경우
    @Transactional
    public void saveValidate(Long postId, Long fromUserId, ValidateRequest validateRequest) {
        Post post = dataJpaPostRepository.findById(postId).orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 포스트가 없음"));
        User fromUser = dataJpaUserRepository.findById(fromUserId).orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 작성자가 없음"));

        for (UserValidate userValidate: validateRequest.getUserValidate()) {
            if (!fromUserId.equals(userValidate.getUserId())) {
                User toUser = dataJpaUserRepository.findById(userValidate.getUserId()).orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 유저 없음"));
                Template template = templateRepository.findTemplateByContent(userValidate.getTemplateName()).orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 유저 없음"));
                validateRepository.save(Validate.builder()
                        .post(post)
                        .fromUser(fromUser)
                        .toUser(toUser)
                        .template(template)
                        .build());
            }
        }
    }


    // 포스트 아이디를 받아서 포함된 멈버를 다 가져오고, 템플릿도 모두 받아와서 보내준다
    public MembersAndTemplates getParticipateMember(Long postId) {
//        Post post = dataJpaPostRepository.findById(postId).orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 포스트가 없음"));
//        User writer = post.getUser();
//        List<PostUser> postUsers = dataJpaPostRepository.findById(postId)
//                .orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 포스트가 없음"))
//                .getPostUsers();
//        List<String> userNames = new ArrayList<>();
//        for(PostUser p : postUsers) {
//            if(p != null && p.getUser() != writer) {
//                userNames.add(p.getUser().getName());
//            }
//        }
//        Post post = dataJpaPostRepository.findById(postId).orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 포스트가 없음"));
//        User writer = post.getUser();

        Post post = dataJpaPostRepository.findById(postId)
                .orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 포스트가 없음"));
        User writer = post.getUser();
        List<String> userNickNames = dataJpaPostRepository.findById(postId)
                .orElseThrow(() -> new ObjectNotFound("아이디에 해당하는 포스트가 없음"))
                .getPostUsers().stream()
                .filter(p -> p != null && !p.getUser().equals(writer))
                .map(p -> p.getUser().getName())
                .toList();


//        List<Template> templates = templateRepository.findAll();
//        List<String> templateNames = new ArrayList<>();
//        for(Template template : templates) {
//            if(Objects.nonNull(template)) {
//                templateNames.add(template.getContent());
//            }
//        }

        List<String> templateNames = templateRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(Template::getContent)
                .toList();

        return MembersAndTemplates.builder().writer(writer.getNickname()).members(userNickNames).templateNames(templateNames).build();

    }

    // 모임 시작됐을 때 저장되는 경우
    public void saveValidate2(Long postId, Long fromUserId) {
        Post post = dataJpaPostRepository.findById(postId).get();
        User fromUser = dataJpaUserRepository.findById(fromUserId).get();

        validateRepository.save(Validate.builder()
                        .post(post)
                        .fromUser(fromUser)
                        .build());

    }

}
