package com.beyond.mvc.common.filter;

/*
    서블릿 필터
    - Request, Response가 서블릿이나 JSP에 도달하기 전에 필요한 전/후처리 작업을 수행하는 필터
    - Filter Chain(객체)을 통해 여러 필터를 연속적으로 사용할 수 있다.

    구현
    1. 필터 인터페이스를 구현하는 클래스 생성
    2. doFilter() 재정의
        - filterChain.doFilter() 기준으로 서블릿 전/후 코드 작성
    3. 필터 등록 및 매핑(url 매핑)
        - web.xml 파일에 필터 등록
        - annotation 기반 - @WebFilter()
*/

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "encryptFilter", urlPatterns = {"/auth/login", "/auth/sign-up"})
public class EncryptFilter implements Filter {
    // init, destroy는 필요할 때만 구현하면 되는 default 메소드

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터가 생성될 때 초기화를 위해 호출'
        // System.out.println(filterConfig.getFilterName() + "필터 생성");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 실제 필터가 수행하는 작업들

        // 서블릿 동작 전 실행될 코드들
        // System.out.println("서블릿 이전 작업");
        EncryptPasswordWrapper wrapper = new EncryptPasswordWrapper((HttpServletRequest) request);

        // 다음 필터 호출 / 서블릿, jsp에 전달
        filterChain.doFilter(wrapper, response);

        // 서블릿 동작 후 실행될 코드들
        // System.out.println("서블릿 후 돌아오는 객체들에 대해 실행될 코드들");
    }

    @Override
    public void destroy() {
        // 필터가 소멸 시 리소스 정리를 위해 호출
        // System.out.println("필터 소멸");
    }
}
