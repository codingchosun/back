package com.codingchosun.backend.controller;


import com.codingchosun.backend.request.ValidateRequest;
import com.codingchosun.backend.response.MembersAndTemplates;
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
@RequestMapping("/bridge/validate/post/{postId}")
public class ValidateController {
    private final ValidateService validateService;

    // 모임 시작할 때
    @PostMapping("/user/{fromUserId}")
    public HttpEntity<Map<String, LocalDateTime>> saveValidate(@PathVariable(name = "postId") Long postId,
                                         @PathVariable(name = "fromUserId") Long fromUserId,
                                         @RequestBody @Valid ValidateRequest validateRequest,
                                         BindingResult bindingResult) {
        CatchMissingValueUtils.throwMissingValue(bindingResult);
        validateService.saveValidate(postId, fromUserId, validateRequest);
        return new ResponseEntity<>(Map.of("createdAt", LocalDateTime.now()), HttpStatus.CREATED);
    }


    // 포스트 아이디를 받아서 포함된 멈버를 다 가져오고, 템플릿도 모두 받아와서 보내준다
    // ToDo 약속장 고려해야 함
    @GetMapping("/members")
    public HttpEntity<MembersAndTemplates> getParticipateMember(@PathVariable(name = "postId") Long postId) {
        return new ResponseEntity<>(validateService.getParticipateMember(postId), HttpStatus.OK);
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
