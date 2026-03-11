package com.beyond.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {

    @RequestMapping("/")
    public String home() {

        // 뷰의 이름
        return "home";
    }

}
