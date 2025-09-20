package com.codingchosun.backend.controller.evaluation;

import com.codingchosun.backend.dto.request.EvaluationRequest;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.EvaluationResponse;
import com.codingchosun.backend.service.evaluation.EvaluationQueryService;
import com.codingchosun.backend.service.evaluation.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final EvaluationQueryService evaluationQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveEvaluation(@PathVariable(name = "postId") Long postId,
                                                              @RequestBody @Valid List<EvaluationRequest> requests,
                                                              @AuthenticationPrincipal UserDetails userDetails) {
        evaluationService.saveEvaluation(postId, userDetails.getUsername(), requests);

        return ApiResponse.ok("평가가 성공적으로 제출되었습니다.");
    }

    //평가 참여자 목록 조회 api
    @GetMapping("/targets")
    public ResponseEntity<ApiResponse<EvaluationResponse>> getEvaluationTargets(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        EvaluationResponse response = evaluationQueryService.getTargetsToEvaluate(postId, userDetails.getUsername());

        return ApiResponse.ok(response);
    }


}
