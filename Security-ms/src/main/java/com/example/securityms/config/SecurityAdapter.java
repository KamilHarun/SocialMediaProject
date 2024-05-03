package com.example.securityms.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class SecurityAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenService jwtTokenService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("Start configure filter");
        httpSecurity.addFilterBefore(new SecurityFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class);
    }
}


