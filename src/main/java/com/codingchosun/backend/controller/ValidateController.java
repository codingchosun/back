package com.codingchosun.backend.controller;


import com.codingchosun.backend.request.ValidateRequest;
import com.codingchosun.backend.response.MembersAndTemplates;
import com.codingchosun.backend.response.UpdateUsersManner;
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
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/bridge/validate/post/{postId}")
public class ValidateController {
    private final ValidateService validateService;

    // 모임 시작할 때
    @PostMapping("/user/{fromUserId}")
    public HttpEntity<List<UpdateUsersManner>> saveValidate(@PathVariable(name = "postId") Long postId,
                                         @PathVariable(name = "fromUserId") Long fromUserId,
                                         @RequestBody @Valid ValidateRequest validateRequest,
                                         BindingResult bindingResult) {
        CatchMissingValueUtils.throwMissingValue(bindingResult);

        return new ResponseEntity<>(validateService.saveValidate(postId, fromUserId, validateRequest), HttpStatus.CREATED);
    }


    // 포스트 아이디를 받아서 포함된 멈버를 다 가져오고, 템플릿도 모두 받아와서 보내준다
    @GetMapping("/members")
    public HttpEntity<MembersAndTemplates> getParticipateMember(@PathVariable(name = "postId") Long postId) {
        return new ResponseEntity<>(validateService.getParticipateMember(postId), HttpStatus.OK);
    }



}
