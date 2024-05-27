package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Hashtag;
import com.codingchosun.backend.domain.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class HashtagDto {
    private Long hashtagId;
    private String hashtagName;

    public HashtagDto(Hashtag hashtag){
        this.hashtagId = hashtag.getHashtagId();
        this.hashtagName = hashtag.getHashtagName();
    }
}
