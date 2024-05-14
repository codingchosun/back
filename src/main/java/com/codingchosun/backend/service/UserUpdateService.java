package com.codingchosun.backend.service;


import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserUpdateService {

    private DataJpaUserRepository userRepository;
    @Autowired
    public UserUpdateService(DataJpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void updateUser(User user, UserUpdateRequest updateRequest) {
        user = userRepository.findById(user.getUserId()).orElseThrow(()
                -> new IllegalArgumentException("해당하는 아이디가 없습니다 id : "));

        if (updateRequest.getPassword() != null) {
            user.setPassword(updateRequest.getPassword());
        }
        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getGenderCode() != null) {
            user.setGenderCode(updateRequest.getGenderCode());
        }
        if (updateRequest.getNickname() != null) {
            user.setNickname(updateRequest.getNickname());
        }
        if (updateRequest.getIntroduction() != null) {
            user.setIntroduction(updateRequest.getIntroduction());
        }
        userRepository.save(user);
    }


}



