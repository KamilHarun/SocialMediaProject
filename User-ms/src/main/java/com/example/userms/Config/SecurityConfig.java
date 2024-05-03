package com.example.userms.Config;
import com.example.securityms.config.GeneralConfig;
import com.example.securityms.config.JwtService;
import com.example.securityms.config.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Import({JwtTokenService.class, JwtService.class})

public class SecurityConfig extends GeneralConfig {
    public SecurityConfig(UserDetailsService userDetailsService, JwtTokenService jwtTokenService) {
        super(userDetailsService, jwtTokenService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll()
                .requestMatchers( HttpMethod.GET, "/api/v1/addresses/**").authenticated()
                .requestMatchers(HttpMethod.GET , "/api/v1/users/findById").permitAll()
                .requestMatchers(HttpMethod.GET , "/api/v1/users/all").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()


        );
        return super.securityFilterChain(http);

    }
}
