package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.response.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginCheckController {

    private final DataJpaUserRepository dataJpaUserRepository;

    //로그인된 user 정보 가져오기
    @GetMapping("/getloginuser")
    public HttpEntity<UserDTO> getUser(@AuthenticationPrincipal UserDetails userDetails){
        //로그인 검사
        if(userDetails == null){
            throw new LoggedInUserNotFound("로그인불량");
        }
        User user = this.getUserFromUserDetails(userDetails);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    public User getUserFromUserDetails(UserDetails userDetails){
        return dataJpaUserRepository.findByLoginId(userDetails.getUsername());
    }
}
