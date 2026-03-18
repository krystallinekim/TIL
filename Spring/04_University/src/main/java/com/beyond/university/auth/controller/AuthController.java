package com.beyond.university.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class AuthController {

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {

        log.info("로그인 페이지 요청");

        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

}
