package com.codingchosun.backend.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest {

    @NotEmpty
    private String nickname;

    private String introduction;

    @NotEmpty
    private String email;

    private int score;

    private List<String> hashNames;

    //일단 무슨 평가를 제일 많았는지는 제외 (어떻게해야할지 모르겠음)

}
