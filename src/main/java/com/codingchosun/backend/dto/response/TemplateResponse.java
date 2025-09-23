package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.Template;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TemplateResponse {

    private int score;
    private String content;

    public TemplateResponse(int score, String content) {
        this.score = score;
        this.content = content;
    }

    public static TemplateResponse from(Template template) {
        return new TemplateResponse(
                template.getScore(),
                template.getContent()
        );
    }
}