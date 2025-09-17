package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HashtagDto {

    private Long hashtagId;

    private String hashtagName;

    public static HashtagDto from(Hashtag hashtag) {
        return new HashtagDto(
                hashtag.getHashtagId(),
                hashtag.getHashtagName()
        );
    }

    public HashtagDto(Hashtag hashtag) {
        this.hashtagId = hashtag.getHashtagId();
        this.hashtagName = hashtag.getHashtagName();
    }
}
