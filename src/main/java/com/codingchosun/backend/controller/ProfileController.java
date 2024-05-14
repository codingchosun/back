package com.codingchosun.backend.controller;


import com.codingchosun.backend.request.ProfileRequest;
import com.codingchosun.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile/{nickname}")
    @ResponseBody
    public ProfileRequest viewProfile(@PathVariable String nickname, Model model) {
        return profileService.getProfile(nickname);
    }
}
