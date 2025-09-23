package com.codingchosun.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRequest {

    @NotNull
    private String toUserLoginId;

    @NotNull
    private Long templateId;

}
