package com.codingchosun.backend.security;

import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {

    private final DataJpaUserRepository userRepository;
    private final AccountDetailSupplier accountDetailSupplier;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new UsernameNotFoundException("유저를 찾을 수 없습니다")
        );

        return accountDetailSupplier.supply(user);
    }
}
