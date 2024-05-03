package com.example.postms.Config;

import com.example.securityms.config.GeneralConfig;
import com.example.securityms.config.JwtService;
import com.example.securityms.config.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@Import({JwtTokenService.class, JwtService.class})
public class SecurityConfig extends GeneralConfig {

    public SecurityConfig(UserDetailsService userService, JwtTokenService jwtTokenService) {
        super(userService, jwtTokenService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/v1/posts").permitAll()
                .requestMatchers("/api/v1/posts/**").permitAll()
                .requestMatchers("api/v1/likes/**").permitAll()
        );
        return super.securityFilterChain(http);
    }
}
