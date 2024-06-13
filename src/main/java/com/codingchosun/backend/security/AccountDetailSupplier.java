package com.codingchosun.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import com.codingchosun.backend.domain.User;
import java.util.Collections;
import java.util.List;

@Service
public class AccountDetailSupplier {

    public UserDetails supply(User account) {
        // NormalAccount의 authority 필드를 사용하여 권한 설정
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // UserDetails 객체 생성
        return new org.springframework.security.core.userdetails.User(
                account.getLoginId(),
                account.getPassword(),
                authorities // 권한 리스트
        );
    }
}
