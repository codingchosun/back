package com.codingchosun.backend.service.user;


import com.codingchosun.backend.domain.Hashtag;
import com.codingchosun.backend.domain.Template;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.domain.UserHash;
import com.codingchosun.backend.dto.request.ProfileResponse;
import com.codingchosun.backend.dto.request.UserUpdateRequest;
import com.codingchosun.backend.dto.response.UpdateProfileResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.repository.hashtag.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtag.DataJpaUserHashRepository;
import com.codingchosun.backend.repository.template.DataJpaTemplateRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
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
    private final DataJpaTemplateRepository dataJpaTemplateRepository;
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

        List<String> templateNames = dataJpaTemplateRepository.findByEvaluations_ToUser_UserId(user.getUserId()).stream()
                .map(Template::getContent)
                .toList();

        return new ProfileResponse(
                user.getLoginId(),
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

        if (userUpdateRequest.getCurrentPassword() != null && userUpdateRequest.getNewPassword() != null) {
            user.updatePassword(userUpdateRequest.getCurrentPassword(), userUpdateRequest.getNewPassword(), passwordEncoder);
        }
        if (userUpdateRequest.getNickname() != null) {
            user.setNickname(userUpdateRequest.getNickname());
        }
        if (userUpdateRequest.getIntroduction() != null) {
            user.setIntroduction(userUpdateRequest.getIntroduction());
        }
        if (userUpdateRequest.getHashtags() != null) {
            updateUserHashtag(user, userUpdateRequest.getHashtags());
        }

        return UpdateProfileResponse.from(user);
    }

    private void updateUserHashtag(User user, List<String> newHashtags) {
        userHashRepository.deleteAll(user.getUserHashes());
        user.getUserHashes().clear();

        for (String hashtagName : newHashtags) {
            Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName).orElseGet(
                    () -> hashtagRepository.save(new Hashtag(hashtagName))
            );
            UserHash userHash = new UserHash(user, hashtag);
            user.getUserHashes().add(userHash);
            userHashRepository.save(userHash);
        }
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED)
        );
    }
}