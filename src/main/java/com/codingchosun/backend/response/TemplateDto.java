package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Template;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {

    private Long templateId;
    private String content;

    public static TemplateDto from(Template template) {
        return new TemplateDto(
                template.getTemplateId(),
                template.getContent()
        );
    }
}
