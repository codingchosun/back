package com.codingchosun.backend.security;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {

    private DataJpaUserRepository userRepository;
    private AccountDetailSupplier accountDetailSupplier;

    @Autowired
    public AccountDetailService(DataJpaUserRepository userRepository, AccountDetailSupplier accountDetailSupplier) {
        this.userRepository = userRepository;
        this.accountDetailSupplier = accountDetailSupplier;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId);
        //Set<Role> roles = accountRoleReader.findRolesByAccountId(normalAccount.getId());
        return accountDetailSupplier.supply(user);
    }
}
