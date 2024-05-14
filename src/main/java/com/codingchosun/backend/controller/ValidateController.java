package com.codingchosun.backend.controller;


import com.codingchosun.backend.request.ValidateRequest;
import com.codingchosun.backend.service.ValidateService;
import com.codingchosun.backend.web.CatchMissingValueUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/bridge/validate/post/{postId}/user/{fromUserId}")
public class ValidateController {
    private final ValidateService validateService;

    // 모임 시작할 때
    @PostMapping
    public HttpEntity<Map<String, LocalDateTime>> saveValidate(@PathVariable(name = "postId") Long postId,
                                         @PathVariable(name = "fromUserId") Long fromUserId,
                                         @RequestBody @Valid ValidateRequest validateRequest,
                                         BindingResult bindingResult) {
        CatchMissingValueUtils.throwMissingValue(bindingResult);
        validateService.saveValidate(postId, fromUserId, validateRequest);
        return new ResponseEntity<>(Map.of("createdAt", LocalDateTime.now()), HttpStatus.CREATED);
    }
//    @PostMapping
//    public HttpEntity<Map<String, LocalDateTime>> saveValidate2(@PathVariable(name = "postId") Long postId,
//                                         @PathVariable(name = "fromUserId") Long fromUserId,
//                                         BindingResult bindingResult) {
//        CatchMissingValueUtils.throwMissingValue(bindingResult);
//        validateService.saveValidate2(postId, fromUserId);
//        return new ResponseEntity<>(Map.of("createdAt", LocalDateTime.now()), HttpStatus.CREATED);
//    }
}
