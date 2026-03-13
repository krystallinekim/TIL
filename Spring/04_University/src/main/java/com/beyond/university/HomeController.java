package com.beyond.university;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        List<String> names = Arrays.asList("홍길동", "이몽룡", "성춘향", "김영희", "김철수");

        // modelAndView.addObject("message", "안녕하세요.");
        modelAndView.addObject("names", names);
        modelAndView.setViewName("home");

        return modelAndView;
    }
}
