package com.example.messagems.Config;

import com.example.securityms.config.GeneralConfig;
import com.example.securityms.config.JwtService;
import com.example.securityms.config.JwtTokenService;
import org.apache.kafka.common.requests.ApiError;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@Import({JwtService.class, JwtTokenService.class})
public class SecurityConfig extends GeneralConfig {

    public SecurityConfig(UserDetailsService userService, JwtTokenService jwtTokenService) {
        super(userService, jwtTokenService);
    }

    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/messages").permitAll()
                .requestMatchers("api/v1/emails/send").permitAll()
        );
        return super.securityFilterChain(http);
    }
}
