package com.beyond.university.student.controller;

import com.beyond.university.department.model.service.DepartmentService;
import com.beyond.university.department.model.vo.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StudentController {

    // final이니 생성자로 받음(@RequiredArgsConstructor)
    private final DepartmentService departmentService;

    @GetMapping("/student/search")
    // 반환이 void면 매핑 URL 기준으로 /student/search를 찾는다.
    public ModelAndView search(ModelAndView modelAndView) {
        log.info("search() 메소드 호출");

        List<Department> departments = departmentService.getDepartment();
        System.out.println(departments.size());
        System.out.println(departments.getFirst());



        modelAndView.setViewName("student/search");
        modelAndView.addObject("categories");



        return modelAndView;
    }

}
