package com.codingchosun.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindLoginIdRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

}
