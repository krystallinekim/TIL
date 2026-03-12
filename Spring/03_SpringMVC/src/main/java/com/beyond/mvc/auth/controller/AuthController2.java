package com.beyond.mvc.auth.controller;

import com.beyond.mvc.auth.model.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
// @Controller
@RestController
public class AuthController2 {
    // ## 뷰에 데이터 전달


    // 1. Model 객체 사용
    // Model 객체는 컨트롤러에서 뷰로 데이터를 전달하려고 할 때 사용하는 객체이다.
    // 전달할 데이터를 Map(key-value) 형식으로 담을 수 있다. 이후 영역객체에 담은 것처럼 JSP 페이지에서 EL로 확인할 수 있다.
    // request scope 내부에서 유효함
    @GetMapping("/auth/users/model/{no}")
    public String users(Model model, @PathVariable int no) {

        log.info("userNo: {}", no);
        model.addAttribute("user", new UserResponseDto(no, "Hong123", "seoul"));

        return "auth/profile";
    }

    // 2. ModelAndView 객체
    // - 컨트롤러에서 뷰로 전달할 데이터와 뷰에 대한 정보를 다 담는 객체
    @GetMapping("/auth/users/modelandview/{no}")
    public ModelAndView users(ModelAndView modelAndView, @PathVariable int no) {

        UserResponseDto responseDto = new UserResponseDto(no, "lee234", "busan");

        // 데이터
        // model.addAttribute("user", responseDto)); ->
        modelAndView.addObject("user", responseDto);

        // 뷰 정보
        // return "auth/profile"; ->
        modelAndView.setViewName("auth/profile");

        return modelAndView;
    }


    // ## 클라이언트로 데이터 전달

    // 1. @ResponseBody 어노테이션
    // 원래 반환형이 String일 경우 뷰 이름으로 인식하지만, @ResponseBody를 붙이면 그냥 문자열로 인식한다.
    // 클라이언트에 직접 데이터를 반환할 수 있다.
    @ResponseBody
    @GetMapping("/data")
    public String data() {
        return "Hello World";
    }

    // java 객체를 클라이언트로 전달
    // 보통 웹에서 객체를 받을 때는 JSON으로 받는다.
    // jackson 라이브러리 의존성만 넣어주면 그대로 됨
    // java 객체와 JSON 객체의 상호 교환 용도
    @ResponseBody
    @GetMapping("/auth/users/json/{no}")
    public Object users_json(@PathVariable int no){

        // 일반적인 객체의 경우
        UserResponseDto dto = new UserResponseDto(no, "lee123", "busan");
        // 클라이언트는 자바 객체를 이해할 수 없다 -> JSON으로 변환해서 받게 해 줘야 함

        // Map
        Map<String, Object> map = new HashMap<>();
        map.put("no", no);  // 숫자
        map.put("name", "홍길동");  // 문자열
        map.put("isAdmin", true);  // 논리값
        map.put("user", dto);  // 객체
        map.put("numbers", new int[]{4,5,6,7,8});  // 배열

        // List
        List<Object> list = new ArrayList<>();
        list.add(null);
        list.add(no);
        list.add("hong123");
        list.add(false);
        list.add(dto);

        // return dto;  // {"no":12,"userName":"lee123","address":"busan"}
        // map, list도 json으로 변환해서 들어감
        // return map;  // {"no":12,"name":"홍길동","numbers":[4,5,6,7,8],"isAdmin":true,"user":{"no":12,"userName":"lee123","address":"busan"}}
        return list;  // [null,12,"hong123",false,{"no":12,"userName":"lee123","address":"busan"}]
    }

    // 2. @RestController 어노테이션 사용 - 클래스 전체에 적용하는 어노테이션
    // - @RestController에는 @ResponseBody와 @Controller가 같이 붙어 있다. 모든 메소드에 기본적으로 @ResponseBody가 붙음
    // - 클래스의 모든 리턴값을 전부 요청바디에 담아서 간다고 생각하면 됨. 리턴값이 페이지 이름이라고 보지 않게 된다.
    @GetMapping("/auth/users/responsebody/{no}")
    public Object users_responsebody(@PathVariable int no) {
        UserResponseDto dto = new UserResponseDto(no, "lee123", "busan");

        return dto;
    }

    // 3. ResponseEntity 객체 사용
    // - HttpEntity 클래스를 상속하는 클래스 - HTTP 응답 데이터를 포함함
    // - 개발자가 직접 HTTP 바디, 헤더, 상태코드 등을 세팅해서 반환할 수 있다.
    @GetMapping("/auth/users/responseentity/{no}")
    public ResponseEntity users_responseentity(@PathVariable int no) {
        // HttpServletResponse 객체를 가져와서 resp.header, resp.statusCode 등등을 작성하는 게 아니라
        // 헤더, 상태코드, 바디에 들어갈 것만 지정해 주면 알아서 변환됨

        // 객체 -> JSON -> HTTP 바디
        UserResponseDto dto = new UserResponseDto(no, "lim325", "daegu");

        // return ResponseEntity.ok(dto);  // 바디만
        // return new ResponseEntity(dto, HttpStatus.CREATED);  // 바디 + 상태코드
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 상태코드만 넘기기도 가능
        // 헤더도 줄 수 있다
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "sdafjlkjkjsadfll")
                .body(dto);  // 바디 전달
    }


    // 3-1. ResponseBody를 이용해 같은 작업을 할 경우
    // 똑같지만, 원래 헤더/상태코드 세팅이 안된다. 반환하는건 응답 바디에만 들어감
    // 같은 작업을 하려면 응답 객체를 따로 얻어와야함
    @GetMapping("/auth/users/responsebody_entity/{no}")
    @ResponseBody
    public UserResponseDto users_entity_body(@PathVariable int no, HttpServletResponse response) {

        UserResponseDto dto = new UserResponseDto(no, "lim325", "daegu");

        // 코드는 간단해 보이지만, 결국 객체를 하나 더 가져와야 한다
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Authorization", "adgknragaonerr");

        return dto;
    }

    // 나중에 REST API 할떄는 ResponseEntity를 반환하는 식으로 작성하게 될 것


}
