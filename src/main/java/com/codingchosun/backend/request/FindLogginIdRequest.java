package com.codingchosun.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindLogginIdRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

}
