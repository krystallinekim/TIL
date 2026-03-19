package com.beyond.university.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 사용자 권한 정보 받아오기 - 권한을 문자열로 추출해서 리스트화
        List<String> roles = authentication
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        log.info("Authorities: {}", roles);

        // 관리자 권한이 있으면 admin/info로 리다이렉트
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/info");
        } else {
            response.sendRedirect("/");
        }


    }
}
