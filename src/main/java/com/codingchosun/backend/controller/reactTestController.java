package com.codingchosun.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class reactTestController {

    @PostMapping
    public StateDto postTest(@RequestBody TestDTO testDTO) {
        log.info("before: " + testDTO.toString());
        testDTO.setTitle("title modify");
        testDTO.setContents("contents modify");
        log.info("after: " + testDTO.toString());

        StateDto state = new StateDto();
        state.setState("success");
        return state;
    }

    @Data
    public static class StateDto{
        private String state;
    }

    @Data
    public static class TestDTO{
        private String title;
        private String contents;
    }
}
