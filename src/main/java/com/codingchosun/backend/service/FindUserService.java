package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.FindLogginIdRequest;
import com.codingchosun.backend.request.FindPasswordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class FindUserService {

    DataJpaUserRepository userRepository;

    @Autowired
    public FindUserService(DataJpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String findMyLoginId(FindLogginIdRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        Optional <User> user = Optional.ofNullable(userRepository.findByNameAndEmail(name, email));
        return user.map(User::getLoginId).orElse(null);
    }

    public String findMyPassword(FindPasswordRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String id = request.getLoginId();
        Optional<User> user = Optional.ofNullable(userRepository.findByNameAndEmailAndLoginId(name, email, id));
        return user.map(User::getPassword).orElse(null);

    }
}
