package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.UserRepository;
import com.codingchosun.backend.request.ProfileResponse;
import com.codingchosun.backend.request.UserUpdateRequest;
import com.codingchosun.backend.service.ProfileService;
import com.codingchosun.backend.service.UserUpdateService;
import com.codingchosun.backend.web.argumentresolver.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final UserUpdateService userUpdateService;
    private final UserRepository userRepository;

    @Autowired
    public ProfileController(ProfileService profileService, UserUpdateService userUpdateService, UserRepository userRepository) {
        this.profileService = profileService;
        this.userUpdateService = userUpdateService;
        this.userRepository = userRepository;
    }

    //프로필페이지 보내주는 매핑
    @GetMapping("/profile/{nickname}")
    @ResponseBody
    public ProfileResponse viewProfile(@PathVariable String nickname, Model model) {
        return profileService.getProfile(nickname);
    }

    //프로필수정 매핑
    @PostMapping("/profile/{nickname}")
    public HttpEntity<HttpStatus> UpdateProfile(@Login User user, @PathVariable String nickname, UserUpdateRequest userUpdateRequest, Model model, BindingResult bindingResult) {
        User updateUser = userRepository.findByNickname(nickname);
        if (!user.equals(updateUser)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userUpdateService.updateUser(updateUser, userUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
