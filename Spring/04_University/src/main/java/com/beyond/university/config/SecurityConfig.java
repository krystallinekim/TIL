package com.beyond.university.config;

import com.beyond.university.auth.handler.AuthenticationFailureHandlerImpl;
import com.beyond.university.auth.handler.AuthenticationSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity,
        AuthenticationFailureHandler authenticationFailureHandler,
        AuthenticationSuccessHandler authenticationSuccessHandler
    ) throws Exception {
        /*
        * CSRF(Cross-Site Request Forgery)
        *   - 공격자가 사용자의 브라우저를 악용하여 인증된 세션을 가진 사용자의 권한으로 악의적인 요청을 보내는 공격이다.
        *   - 스프링 시큐리티는 기본적으로 CSRF 보호 기능을 활성화하며 GET 요청을 제외한 요청에 대해 CSRF 토큰을 검증한다.
        */
        httpSecurity
            .csrf(Customizer.withDefaults())           // CSRF 공격을 방지하기 위해 서버에서 임의로 토큰을 발급하고, 이 토큰이 있어야만 요청을 처리
            // .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
            .httpBasic(Customizer.withDefaults())      // HTTP 인증 활성화
            // .formLogin(Customizer.withDefaults())   // 폼 로그인 활성화 - 기본 페이지 사용
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")             // 직접 제작한 페이지(/login) 사용
                    // 기본적으로 username, password 파라미터를 사용함
                    // .usernameParameter("userName")  // username 파라미터를 "userId"로 표시
                    // .passwordParameter("userPwd")   // password 파라미터를 "userPwd"로 표시
                    // 요청 실패 시 처리해줄 handler
                    .failureHandler(authenticationFailureHandler)
                    .successHandler(authenticationSuccessHandler)
            )
            // 로그아웃 설정
            .logout(logout ->
                logout
                    .logoutUrl("/logout")                          // 로그아웃 시 보낼 URL(기본값)
                    .logoutSuccessUrl("/login?logout")             // 로그아웃 성공시 이동할 URL(기본값)
                    .invalidateHttpSession(true)                     // 세션 삭제 여부(기본값)
                    .deleteCookies("JSESSIONID")  // 로그아웃 시 지울 쿠키(기본값)
            )
            // 기억하기 기능 - 세션이 종료되어도 로그인정보를 유지할 수 있는 기능
            .rememberMe(rememberMe ->
                rememberMe
                    .key("beyond")                       // 토큰 생성 시 서명에 사용되는 키 -> 키는 노출되면 안됨
                    .tokenValiditySeconds(1209600)       // 쿠키 유효시간(초) 지정
                    // .rememberMeParameter("remember")  // remember-me 파라미터를 "remember"로 표시
            )
            // 세션 관리 기능 - 로그인 세션을 하나만 유지
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .invalidSessionUrl("/login?invalid")  // 세션 만료 시 이동할 경로(자연스럽게 세션 만료 시)
                    .maximumSessions(1)                   // 최대 세션 수
                    .expiredUrl("/login?expired")         // 마지막 연결된 세션이 끊겼을 때 이동할 경로(새로 로그인해서 세션이 끊겼을 때)
            )
            .exceptionHandling(exceptionHandler ->
                exceptionHandler
                    .accessDeniedPage("/access-denied")  // 권한 없는 계정에서 잘못된 접근 시 이동할 URL
            )
            // 접근 제어 설정
            .authorizeHttpRequests(authorizationRequest ->
                authorizationRequest
                    .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()  // 정적 리소스를 언제나 허용하도록 설정
                    .requestMatchers("/login").permitAll()  // 로그인 페이지는 인증에서 제외
                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // USER, ADMIN은 user 요청에 접근 가능
                    .requestMatchers("/admin/**").hasRole("ADMIN")  // ADMIN만 admin 요청에 접근 가능
                    .anyRequest().authenticated()             // 들어오는 모든 요청에 대해 인증정보가 없다면 받지 않음
            );

        return httpSecurity.build();
    }

    // UserDetailsService: 전달받은 정보를 통해 사용자를 찾아 UserDetails 객체를 생성 후 반환한다.
    /*
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // 1. 인 메모리 방식
        UserDetails admin = User.builder()
            .username("admin")
            // password 비교를 알아서 해준다.
            // .password("{noop}1234")
            .password(passwordEncoder.encode("a1234"))
            .roles("ADMIN", "USER")
            .build();

        UserDetails user = User.builder()
            .username("user")
            // .password("{noop}5678")
            .password(passwordEncoder.encode("u1234"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    */

    // Authentication Manager
    // - 사용자에 대한 인증 관련 설정을 하는 객체
    // - 원래 알아서 만들어준다(생략 가능함). 더 만들수도 있음
    @Bean
    public AuthenticationManager authenticationManager(
        PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    // 비밀번호를 BCrypt 방식으로 인코딩해서 반환함
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // 인증 관련 실패가 있을 경우 이를 처리하는 Handler
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }

}
