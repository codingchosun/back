package com.codingchosun.backend.controller;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.invalidrequest.DeletedUserException;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.ProfileResponse;
import com.codingchosun.backend.request.UserUpdateRequest;
import com.codingchosun.backend.service.ProfileService;
import com.codingchosun.backend.service.UserUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileService profileService;
    private final UserUpdateService userUpdateService;
    private final DataJpaUserRepository userRepository;
    @Autowired
    public ProfileController(ProfileService profileService, UserUpdateService userUpdateService, DataJpaUserRepository userRepository) {
        this.profileService = profileService;
        this.userUpdateService = userUpdateService;
        this.userRepository = userRepository;
    }

    //프로필페이지 보내주는 매핑
    @GetMapping("/profile/{loginId}")
    @ResponseBody
    public ProfileResponse viewProfile(@PathVariable String loginId, Model model) {
        User user = userRepository.findByStateAndLoginId(StateCode.ACTIVE, loginId);
        if(user == null) {throw new DeletedUserException("탈퇴된 유저 입니다.");}
        return profileService.getProfile(loginId);
    }

    //프로필수정 매핑
    @PostMapping("/profile/{loginId}")
    public HttpEntity<HttpStatus> UpdateProfile(@AuthenticationPrincipal UserDetails login, @PathVariable String loginId, @RequestBody UserUpdateRequest userUpdateRequest, Model model, BindingResult bindingResult) {
        User updateUser = userRepository.findByLoginId(loginId);
        User user = getUserFromUserDetails(login);
        if (!user.getUserId().equals(updateUser.getUserId())) {
            log.info("403 forbidden");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userUpdateService.updateUser(user, userUpdateRequest);
        userRepository.save(updateUser);
        log.info("profile updated{}", user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public User getUserFromUserDetails(UserDetails userDetails){
        return userRepository.findByLoginId(userDetails.getUsername());
    }
}
