package com.codingchosun.backend.controller;

import com.codingchosun.backend.request.EvaluationRequest;
import com.codingchosun.backend.response.EvaluationResponse;
import com.codingchosun.backend.service.evaludation.EvaluationQueryService;
import com.codingchosun.backend.service.evaludation.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> saveEvaluation(@PathVariable(name = "postId") Long postId,
                                                 @RequestBody @Valid List<EvaluationRequest> requests,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        evaluationService.saveEvaluation(postId, userDetails.getUsername(), requests);

        return ResponseEntity.status(HttpStatus.CREATED).body("평가가 성공적으로 제출되었습니다.");
    }

    //평가 참여자 목록 조회 api
    @GetMapping("/targets")
    public ResponseEntity<EvaluationResponse> getEvaluationTargets(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        EvaluationResponse response = evaluationQueryService.getTargetsToEvaluate(postId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
