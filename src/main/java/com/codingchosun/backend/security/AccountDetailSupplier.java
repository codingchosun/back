package com.codingchosun.backend.security;

import com.codingchosun.backend.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AccountDetailSupplier {

    public UserDetails supply(User account) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getRole().name());

        List<GrantedAuthority> authorities = Collections.singletonList(authority);

        return new org.springframework.security.core.userdetails.User(
                account.getLoginId(),
                account.getPassword(),
                authorities
        );
    }
}
