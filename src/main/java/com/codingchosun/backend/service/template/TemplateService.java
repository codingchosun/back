package com.codingchosun.backend.service.template;

import com.codingchosun.backend.domain.Template;
import com.codingchosun.backend.dto.request.TemplateCreateRequest;
import com.codingchosun.backend.repository.template.DataJpaTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TemplateService {

    private final DataJpaTemplateRepository templateRepository;

    public void createTemplate(TemplateCreateRequest templateCreateRequest) {
        templateRepository.save(new Template(templateCreateRequest.getContent(), templateCreateRequest.getScore()));
    }
}
