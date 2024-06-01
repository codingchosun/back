package com.codingchosun.backend.service;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeleteAccountService {
    DataJpaUserRepository userRepository;

    @Autowired
    public DeleteAccountService(DataJpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteAccount(User user) {
        user.setState(StateCode.INACTIVE);
        userRepository.save(user);
    }

}
