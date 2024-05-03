package com.example.userms.Service.Impl;

import com.example.userms.Model.Users;
import com.example.userms.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username is : {}" , username);
        Users usersDb = userRepo.findByName(username).orElseThrow(()->
                new RuntimeException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(usersDb.getName())
                .password(usersDb.getPassword())
                .authorities(usersDb.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getUserAuthority().name()))
                        .collect(Collectors.toList()))

                .build();
    }

}





