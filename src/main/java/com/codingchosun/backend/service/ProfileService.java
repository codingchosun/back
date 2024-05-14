package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.Hashtag;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.domain.UserHash;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaUserHashRepository;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.ProfileRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final DataJpaUserRepository userRepository;
    private final DataJpaHashtagRepository hashtagRepository;
    private final DataJpaUserHashRepository userHashRepository;

    public ProfileService(DataJpaUserRepository userRepository, DataJpaHashtagRepository hashtagRepository, DataJpaUserHashRepository userHashRepository) {
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
        this.userHashRepository = userHashRepository;
    }

    public ProfileRequest getProfile(String nickname) {

        User profileUser = userRepository.findByNickname(nickname);
        Long userId = profileUser.getUserId();
        List<UserHash> userHashes = userHashRepository.findHashtagsByUser_UserId(userId);
        List<Long> hashtagIds = userHashes.stream().map(UserHash::getHashtag).map(Hashtag::getHashtagId).collect(Collectors.toList());
        List<Hashtag> hashtags = hashtagRepository.findByHashtagIdIn(hashtagIds);
        List<String> hashtagNames = hashtags.stream().map(Hashtag::getHashtagName).toList();

//닉네임으로 유저아이디 찾고 유저아이디 저장
        return ProfileRequest.builder()
                .nickname(nickname)
                .introduction(profileUser.getIntroduction())
                .email(profileUser.getEmail())
                .score(profileUser.getScore())
                .hashNames(hashtagNames)
                .build();
    }
}