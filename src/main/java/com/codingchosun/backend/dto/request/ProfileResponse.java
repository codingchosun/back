package com.codingchosun.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String nickname;

    private String introduction;

    private String email;

    private int score;

    private List<String> hashNames;

    private List<String> templateNames;

}
