package com.beyond.university.auth.controller;

import com.beyond.university.auth.model.dto.LoginRequestDto;
import com.beyond.university.auth.model.dto.LoginResponse;
import com.beyond.university.auth.model.service.AuthService;
import com.beyond.university.auth.model.service.JwtCookieService;
import com.beyond.university.common.model.dto.BaseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/*
    인증 관련 API
    1. 로그인(완료)
      - POST /api/v1/auth/login

    2. 로그아웃(완료)
      - POST /api/v1/auth/logout

    3. 토큰 재발급(진행중)
      - POST /api/v1/auth/refresh
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth APIs", description = "인증 관련 API 목록")
public class AuthController {
    private final AuthService authService;
    private final JwtCookieService jwtCookieService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디와 패스워드를 JSON 문자열로 받아서 로그인한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL_SERVER_ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<LoginResponse>> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto) {

        LoginResponse loginResponse = authService.login(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );

        String refreshToken = authService.createRefreshToken(loginResponse.getUsername());
        ResponseCookie cookie = jwtCookieService.createRefreshTokenCookie(refreshToken, Duration.ofDays(1));
        HttpHeaders headers = jwtCookieService.createRefreshTokenCookieHeaders(cookie);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new BaseResponseDto<>(HttpStatus.OK, loginResponse));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "액세스 토큰을 받아 로그아웃함")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "NO_CONTENT",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL_SERVER_ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<Void> logout(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String bearerToken
    ) {
        authService.logout(bearerToken);

        // 0초짜리 내용없는 쿠키를 만들어서 전달함
        ResponseCookie responseCookie = jwtCookieService.deleteRefreshTokenCookie();
        HttpHeaders header = jwtCookieService.createRefreshTokenCookieHeaders(responseCookie);

        return ResponseEntity
                .noContent()
                .headers(header)
                .build();
    }
}
