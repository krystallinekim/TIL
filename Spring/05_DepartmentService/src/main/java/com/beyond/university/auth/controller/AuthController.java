package com.beyond.university.auth.controller;

import com.beyond.university.auth.model.dto.LoginRequestDto;
import com.beyond.university.auth.model.dto.LoginResponse;
import com.beyond.university.auth.model.service.AuthService;
import com.beyond.university.common.model.dto.BaseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    인증 관련 API
    1. 로그인
      - POST /api/v1/auth/login

    2. 로그아웃
      - POST /api/v1/auth/logout

    3. 토큰 재발급
      - POST /api/v1/auth/refresh
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth APIs", description = "인증 관련 API 목록")
public class AuthController {
    private final AuthService authService;

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

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, loginResponse));
    }
}
