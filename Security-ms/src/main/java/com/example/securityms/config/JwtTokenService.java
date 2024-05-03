package com.example.securityms.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(this::isBearerToken)
                .map(this::getAuthentication);
    }

    private boolean isBearerToken(String header) {
        return header.toLowerCase().startsWith("bearer ");
    }

    private Authentication getAuthentication(String header) {
        String token = header.substring("bearer ".length()).trim();
        Claims claims = jwtService.parseToken(token);
        if (claims.getExpiration().before(new Date())) {
            return null;
        }
        return getAuthentication(claims);
    }

    private Authentication getAuthentication(Claims claims) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        log.info("User is : {}", userDetails);
        List<String> authorityList = claims.get("authority", List.class);
        List<GrantedAuthority> authorities = authorityList.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails,null, authorities);
    }
}

