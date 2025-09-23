package com.codingchosun.backend.service.template;

import com.codingchosun.backend.domain.Template;
import com.codingchosun.backend.dto.response.TemplateDto;
import com.codingchosun.backend.dto.response.TemplateResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.TemplateNotFoundFromDB;
import com.codingchosun.backend.repository.template.DataJpaTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateQueryService {

    private final DataJpaTemplateRepository templateRepository;

    public List<TemplateDto> getAllTemplates() {
        return templateRepository.findAll().stream()
                .map(TemplateDto::from)
                .toList();
    }

    public TemplateResponse getTemplate(String templateId) {
        Template template = templateRepository.findByTemplateId(Long.valueOf(templateId)).orElseThrow(
                () -> new TemplateNotFoundFromDB(ErrorCode.TEMPLATE_NOT_FOUND)
        );

        return TemplateResponse.from(template);
    }
}
