package com.beyond.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {
    // @Slf4j(lombok)을 쓰면 다음 선언은 자동으로 해준다.
    // private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String home() {

        log.info("home() 메소드 호출");
        // log.debug("home() 메소드 호출 [DEBUG]");
        // log.error("home() 메소드 호출 [ERROR]");

        // 뷰의 이름
        return "home";
    }

}
