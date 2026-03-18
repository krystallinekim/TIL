package com.beyond.university.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class AuthController {

    /*
    // Salt 기법 - 매번 랜덤한 솔트 값을 이용해 암호화함
    @Autowired
    private PasswordEncoder passwordEncoder;
    */

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {

        log.info("로그인 페이지 요청");

        /*
        // 소금을 쳤으니 돌릴 때마다 암호화된 값이 다르게 나온다.
        // -> 그래서 DB의 암호화된 값과 같은지 비교가 불가능
        System.out.println(passwordEncoder.encode("1234"));
        System.out.println(passwordEncoder.encode("5678"));

        // passwordEncoder.matches() 메소드를 이용해 소금친 값이 맞는지를 확인한다.
        System.out.println(passwordEncoder.matches("1234", "$2a$10$aBXSeJxK/Oc5s4OG5Zt72Omoyv.VA8VtwXnMaSMnAqKlJ8hEuQ85W"));
        */

        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

}
