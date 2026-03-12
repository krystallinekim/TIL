package com.beyond.mvc.auth.controller;

import com.beyond.mvc.auth.model.dto.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AuthController {

    // 컨트롤러가 처리할 요청을 정의함(URL, Method 등등)
    // @RequestMapping(path = "/auth/login", method = RequestMethod.POST)  // method 속성 생략 시 모든 요청 처리, 선언 시 특정 요청만 처리
    // @PostMapping("/auth/login")  // RequestMapping 대신 @GetMapping, @PostMapping 등을 이용할 수 있다

    // 0. 기본
    // @PostMapping("/auth/login")
    // public String login() {
    //    log.info("로그인 요청");

    //    //return "home";
    //    return "redirect:/";
    // }

    // ## 요청 시 사용자의 파라미터를 전송받는 방법
    // 1. HttpServletRequest 객체(기존 방법)
    // - 원하는 객체를 바로 받아서 사용할 수 있다. request, session, response 등등
    // @PostMapping("/auth/login")
    // public String login(HttpServletRequest request, HttpSession session) {
    //    String userName = request.getParameter("userName");
    //    String password = request.getParameter("password");

    //    // log4j에서 {}는 형식지정자의 역할을 한다.
    //    log.info("username = {}, password = {}", userName, password);
    //    log.info("sessionId = {}", session.getId());

    //    return "redirect:/";
    // }

    // 2-1. @RequesParam 어노테이션 이용
    // - 스프링에서 간편하게 파라미터를 받아올 수 있는 방법
    // - 내부적으로는 HttpServlet에서 파라미터를 검색해서 찾아온다.
    // @PostMapping("/auth/login")
    // 1) @RequestParam(파라미터명)을 변수 앞에 줘서 꺼내올 수 있다.
    // public String login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
    // 2) 파라미터명과 변수명이 같으면 파라미터명은 생략 가능
    // public String login(@RequestParam String userName, @RequestParam String password) {
    // 3) 사실 어노테이션까지도 생략 가능. 단, 기본값과 Required 설정을 할 수 없어진다.
    // public String login(String userName, String password) {

    //    log.info("username = {}, password = {}", userName, password);

    //    return "redirect:/";
    // }

    // 2-2. @RequestParam의 required 속성, defaultValue 속성
    // 존재하지 않는 파라미터를 받으려 하면 에러남 - required = true이기 때문
    // @PostMapping("/auth/login")
    // public String login(
    //     @RequestParam String userName,
    //     @RequestParam String password,
    //     // @RequestParam String address
    //     // @RequestParam(required = false) String address  // null을 넘겨준다.
    //     @RequestParam(defaultValue = "서울") String address  // 아니면 비어있을 때 줄 기본값을 줄 수 있다.
    // ) {
    //    log.info("username = {}, password = {}, address = {}", userName, password, address);
    //    return "redirect:/";
    // }

    // 2-3. 같은 이름의 파라미터가 여러개 들어올 경우
    // - 문자열, 문자열의 배열, 리스트 등으로 쉽게 값을 전달 받을 수 있다.
    // @PostMapping("/auth/login")
    // public String login(
    //     @RequestParam String userName,
    //     @RequestParam String password,
    //     // @RequestParam("hobby") String hobbies  // 문자열 배열로 들어온걸 알아서 하나의 문자열로 바꿔줌
    //     // @RequestParam("hobby") String[] hobbies  // 배열로 받아오는것도 자동
    //     @RequestParam("hobby") List<String> hobbies  // 원하면 리스트로 받아오는것도 편하다
    // ) {
    //    log.info("username = {}, password = {}, hobbies = {}", userName, password, hobbies);
    //    // log.info("username = {}, password = {}, hobbies = {}", userName, password, Arrays.toString(hobbies));
    //    return "redirect:/";
    // }

    // 3. 객체 타입으로 파라미터를 전송받는 방법(요청 파라미터가 많을 경우)
    // 직접 DTO(Data Transfer Object)를 만들어서 파라미터를 객체에 그대로 저장할 수 있다.
    @PostMapping("/auth/login")
    public String login(LoginRequestDto requestDto) {

        log.info("LoginRequestDto: {}", requestDto);
        log.info("username = {}, password = {}, hobbies = {}", requestDto.userName(), requestDto.password(), requestDto.hobby());
        return "redirect:/";
    }

    // 4. @PathVariable 어노테이션 - GET같은 요청에서 URL 경로에 데이터가 들어있을 경우
    // - REST API 등에서, 요청URL 상에서 필요한 값을 가져오고 싶을 경우
    @GetMapping("/auth/users/{no}")
    public String users() {
        
        return "redirect:/";
    }


}
