package com.beyond.university.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*
    JwtTokenProvider
      - 엑세스 토큰(Access Token), 리프레시 토큰(Refresh Token), 인증 객체(Authentication)를 생성한다.
      - 레디스(Redis)에 토큰 저장, 조회, 삭제 등의 작업을 수행한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60L * 30L; // 30분

    // 엑세스 토큰(Access Token)을 생성하는 메소드
    public String createAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims =
                Map.of("username", username, "authorities", authorities, "token_type", "access");

        return jwtUtil.createJwtToken(claims, ACCESS_TOKEN_EXPIRATION);
    }

    // 클라이언트가 헤더를 통해 서버로 전달한 토큰을 추출하는 메소드
    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    // 엑세스 토큰의 무결성과 유효성을 검증하는 메소드
    public boolean isUsableAccessToken(String accessToken) {

        return accessToken != null
                && jwtUtil.validateToken(accessToken);
    }

    // SecurityContext 객체에 저장될 Authentication 객체를 생성하는 메소드
    public Authentication createAuthentication(String token) {
        String username = jwtUtil.getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
