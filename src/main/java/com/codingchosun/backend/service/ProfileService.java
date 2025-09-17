package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.Hashtag;
import com.codingchosun.backend.domain.Template;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.domain.UserHash;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.exception.notfoundfromdb.HashtagNotFoundFromDB;
import com.codingchosun.backend.repository.hashtag.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtag.DataJpaUserHashRepository;
import com.codingchosun.backend.repository.template.TemplateRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.request.ProfileResponse;
import com.codingchosun.backend.request.UserUpdateRequest;
import com.codingchosun.backend.response.UpdateProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final DataJpaUserRepository userRepository;
    private final DataJpaHashtagRepository hashtagRepository;
    private final DataJpaUserHashRepository userHashRepository;
    private final TemplateRepository templateRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String loginId) {
        User user = findUserByLoginId(loginId);

        List<UserHash> userHashes = userHashRepository.findHashtagsByUser_UserId(user.getUserId());
        List<Long> hashtagIds = userHashes.stream()
                .map(UserHash::getHashtag)
                .map(Hashtag::getHashtagId)
                .collect(Collectors.toList());

        List<Hashtag> hashtags = hashtagRepository.findByHashtagIdIn(hashtagIds);

        List<String> hashtagNames = hashtags.stream()
                .map(Hashtag::getHashtagName)
                .toList();

        List<String> templateNames = templateRepository.findByValidates_ToUser_UserId(user.getUserId()).stream()
                .map(Template::getContent)
                .toList();

        return new ProfileResponse(
                user.getNickname(),
                user.getIntroduction(),
                user.getEmail(),
                user.getScore(),
                hashtagNames,
                templateNames
        );
    }

    public UpdateProfileResponse updateProfile(String loginId, UserUpdateRequest userUpdateRequest) {
        User user = findUserByLoginId(loginId);
        log.info("수정 전 유저 정보: {}", user);

        if (userUpdateRequest.getCurrentPassword() != null && userUpdateRequest.getNewPassword() != null) {
            user.updatePassword(userUpdateRequest.getCurrentPassword(), userUpdateRequest.getNewPassword(), passwordEncoder);
        }
        if (userUpdateRequest.getEmail() != null) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getGenderCode() != null) {
            user.setGenderCode(userUpdateRequest.getGenderCode());
        }
        if (userUpdateRequest.getNickname() != null) {
            user.setNickname(userUpdateRequest.getNickname());
        }
        if (userUpdateRequest.getIntroduction() != null) {
            user.setIntroduction(userUpdateRequest.getIntroduction());
        }
        updateUserHashtag(user, userUpdateRequest.getHashList());
        log.info("수정 후 유저 정보: {}", user);

        return UpdateProfileResponse.from(user);
    }

    private void updateUserHashtag(User user, List<String> newHashtags) {
        List<UserHash> userHashes = userHashRepository.findHashtagsByUser_UserId(user.getUserId());
        userHashRepository.deleteAll(userHashes);

        for (String hashtagName : newHashtags) {
            Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName).orElseThrow(
                    () -> new HashtagNotFoundFromDB("이름에 맞는 해시태그를 찾을 수 없습니다")
            );
            userHashRepository.save(new UserHash(user, hashtag));
        }
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new LoggedInUserNotFound("로그인된 유저 정보를 찾을 수 없습니다")
        );
    }
}