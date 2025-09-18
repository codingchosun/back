package com.codingchosun.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponse {
    private List<UserToEvaluateDto> usersToEvaluate;
    private List<TemplateDto> availableTemplates;
}
