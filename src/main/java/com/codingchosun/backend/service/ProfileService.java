package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.Hashtag;
import com.codingchosun.backend.domain.Template;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.domain.UserHash;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaUserHashRepository;
import com.codingchosun.backend.repository.templaterepository.TemplateRepository;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.ProfileResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final DataJpaUserRepository userRepository;
    private final DataJpaHashtagRepository hashtagRepository;
    private final DataJpaUserHashRepository userHashRepository;
    private final TemplateRepository templateRepository;

    public ProfileService(DataJpaUserRepository userRepository, DataJpaHashtagRepository hashtagRepository, DataJpaUserHashRepository userHashRepository, TemplateRepository templateRepository) {
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
        this.userHashRepository = userHashRepository;
        this.templateRepository = templateRepository;
    }

    public ProfileResponse getProfile(String loginId) {
        User profileUser = userRepository.findByLoginId(loginId);
        String nickname = profileUser.getNickname();
        Long userId = profileUser.getUserId();
        List<UserHash> userHashes = userHashRepository.findHashtagsByUser_UserId(userId);
        List<Long> hashtagIds = userHashes.stream().map(UserHash::getHashtag).map(Hashtag::getHashtagId).collect(Collectors.toList());
        List<Hashtag> hashtags = hashtagRepository.findByHashtagIdIn(hashtagIds);
        List<String> hashtagNames = hashtags.stream().map(Hashtag::getHashtagName).toList();
        List<String> templateNames =  templateRepository.findByValidates_ToUser_UserId(userId).stream().map(Template::getContent).toList();

        return ProfileResponse.builder()
                .nickname(nickname)
                .introduction(profileUser.getIntroduction())
                .email(profileUser.getEmail())
                .score(profileUser.getScore())
                .hashNames(hashtagNames)
                .templateNames(templateNames)
                .build();
    }
}