package com.codingchosun.backend.controller.template;

import com.codingchosun.backend.dto.request.TemplateCreateRequest;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.TemplateDto;
import com.codingchosun.backend.dto.response.TemplateResponse;
import com.codingchosun.backend.service.template.TemplateQueryService;
import com.codingchosun.backend.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateQueryService templateQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createTemplate(@RequestBody TemplateCreateRequest templateCreateRequest) {
        templateService.createTemplate(templateCreateRequest);

        return ApiResponse.ok("템플릿이 생성되었습니다.");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TemplateDto>>> getAllTemplates() {
        List<TemplateDto> templates = templateQueryService.getAllTemplates();

        return ApiResponse.ok(templates);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<ApiResponse<TemplateResponse>> getTemplate(@PathVariable String templateId) {
        TemplateResponse template = templateQueryService.getTemplate(templateId);

        return ApiResponse.ok(template);
    }
}
