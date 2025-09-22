package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.constants.GenderCode;
import com.codingchosun.backend.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileResponse {

    @JsonProperty("email")
    private String email;

    @JsonProperty("genderCode")
    private GenderCode genderCode;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("introduction")
    private String introduction;

    @JsonProperty("hashtags")
    private List<String> hashtags;

    public static UpdateProfileResponse from(User user) {
        List<String> hashtags = user.getUserHashes().stream()
                .map(u -> u.getHashtag().getHashtagName())
                .toList();

        return new UpdateProfileResponse(
                user.getEmail(),
                user.getGenderCode(),
                user.getNickname(),
                user.getIntroduction(),
                hashtags
        );
    }
}
