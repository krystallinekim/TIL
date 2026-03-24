package com.beyond.university.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private final RedisTemplate<String, String> redisTemplate;
    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60L * 30L; // 30분
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60L * 60L * 24L; // 1일

    // 엑세스 토큰(Access Token)을 생성하는 메소드
    public String createAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims =
                Map.of("username", username, "authorities", authorities, "token_type", "access");

        return jwtUtil.createJwtToken(claims, ACCESS_TOKEN_EXPIRATION);
    }

    // 리프레시 토큰(Refresh Token)을 생성하는 메소드
    public String createRefreshToken(String username) {
        Map<String, Object> claims =
                Map.of("username", username, "token_type", "refresh");

        String refreshToken = jwtUtil.createJwtToken(claims, REFRESH_TOKEN_EXPIRATION);

        // Redis에 리프레시 토큰을 저장하고, 토큰이 존재하는 동안만 사용 가능하도록
        String refreshKey = String.format("refresh:%s", username);

        redisTemplate.opsForValue().set(refreshKey, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    // 클라이언트가 헤더를 통해 서버로 전달한 토큰을 추출하는 메소드
    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    // 1. 엑세스 토큰의 무결성과 유효성을 검증하는 메소드
    // 2. 블랙리스트에 토큰이 있는지 확인하는 메소드
    // 3. 로그인 시 액세스 토큰만 사용하도록(리프레시 토큰 사용 X)하는 메소드
    public boolean isUsableAccessToken(String accessToken) {

        return accessToken != null
                && jwtUtil.validateToken(accessToken)
                && !isBlackListed(accessToken)
                && isAccessToken(accessToken);
    }


    // SecurityContext 객체에 저장될 Authentication 객체를 생성하는 메소드
    public Authentication createAuthentication(String token) {
        String username = jwtUtil.getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 로그아웃 시 redis의 블랙리스트에 액세스 토큰을 저장
    public void addBlackList(String accessToken) {
        String blackListKey = String.format("blacklist:%s", jwtUtil.getJti(accessToken));

        log.info("Blacklist Key : {}", blackListKey);

        // 액세스 토큰의 만료 시간 동안만 Redis에 액세스 토큰을 적용함(키, 밸류, 만료시간과 만료시간의 종류(밀리초, 나노초 등)를 줘야 함)
        redisTemplate.opsForValue().set(blackListKey, accessToken, ACCESS_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    // 로그아웃 시 redis에서 리프레시 토큰을 삭제함
    public void deleteRefreshToken(String accessToken) {
        String username = jwtUtil.getUsername(accessToken);
        String refreshKey = String.format("refresh:%s", username);

        redisTemplate.delete(refreshKey);
    }

    // 리프레시 토큰의 유효성을 검증하는 메소드
    public boolean isValidRefreshToken(String refreshToken) {
        String username = jwtUtil.getUsername(refreshToken);
        String storedRefreshToken = redisTemplate.opsForValue().get(String.format("refresh:%s", username));
        // 만료시 storedRefreshToken = null, 토큰이 변조되었으면 equals에서 걸림
        return storedRefreshToken != null && storedRefreshToken.equals(refreshToken);
    }

    // 액세스 토큰이 블랙리스트에 등록되어있는지 확인함.
    private boolean isBlackListed(String accessToken) {
        String blackListKey = String.format("blacklist:%s", jwtUtil.getJti(accessToken));

        return redisTemplate.hasKey(blackListKey);  // 키가 들어있으면(블랙리스트에 있으면) true 반환
    }

    // 이 토큰이 액세스토큰과 리프레시토큰 중 어떤 것인지 구분
    private boolean isAccessToken(String accessToken) {

        return jwtUtil.getTokenType(accessToken).equals("access");
    }
}
