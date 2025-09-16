package com.codingchosun.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("loginId")
    private String loginId;

    @JsonProperty("email")
    private String email;

}
