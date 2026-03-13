package com.beyond.university.student.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/student/search")
    // 반환이 void면 매핑 URL 기준으로 /student/search를 찾는다.
    public void search() {
        log.info("search() 메소드 호출");


    }

}
