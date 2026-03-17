package com.beyond.university.student.controller;

import com.beyond.university.department.model.dto.DepartmentsDto;
import com.beyond.university.department.model.service.DepartmentService;
import com.beyond.university.student.model.dto.StudentAddRequestDto;
import com.beyond.university.student.model.dto.StudentsDto;
import com.beyond.university.student.model.service.StudentService;
import com.beyond.university.student.model.vo.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StudentController {
    private final DepartmentService departmentService;

    private final StudentService studentService;

    @GetMapping("/student/search")
    public ModelAndView search(ModelAndView modelAndView, String dno) {
        // List<Department> departments = departmentService.getDepartments();
        List<DepartmentsDto> departments =
                departmentService.getDepartments()
                        .stream()
                        .map(DepartmentsDto::new)
                        .toList();

        log.info("Departments Size: {}", departments.size());

        if (dno != null) {
            List<StudentsDto> students =
                    studentService.getStudentsByDepartmentNo(dno)
                            .stream()
                            .map(StudentsDto::new)
                            .toList();

            log.info("Students Size : {}", students.size());

            modelAndView.addObject("students", students);
        }

        modelAndView.addObject("departments", departments);
        modelAndView.setViewName("student/search");

        return modelAndView;
    }

    @GetMapping("/student/info")
    public ModelAndView info(ModelAndView modelAndView, String sno) {
        Student student = studentService.getStudentByNo(sno);
        List<DepartmentsDto> departments =
                departmentService.getDepartments()
                        .stream()
                        .map(DepartmentsDto::new)
                        .toList();

        log.info("Student No: {}", student.getNo());
        log.info("Departments Size: {}", departments.size());

        modelAndView.addObject("student", student);
        modelAndView.addObject("departments", departments);
        modelAndView.setViewName("student/info");

        return modelAndView;
    }

    @GetMapping("/student/add")
    public ModelAndView add(ModelAndView modelAndView) {
        List<DepartmentsDto> departments =
                departmentService.getDepartments()
                        .stream()
                        .map(DepartmentsDto::new)
                        .toList();

        modelAndView.addObject("departments", departments);
        modelAndView.setViewName("student/add");

        return modelAndView;
    }

    @PostMapping("/student/add")
    public ModelAndView add(ModelAndView modelAndView, StudentAddRequestDto studentAddRequestDto, Model model) {
        int result;
        Student student = studentAddRequestDto.toStudent();

        result = studentService.save(student);

        if (result > 0) {
            modelAndView.addObject("msg", "학생이 등록되었습니다");
            modelAndView.addObject("location", "/");
        } else {
            modelAndView.addObject("msg", "등록에 실패했습니다");
            modelAndView.addObject("location", "/student/add");
        }

        System.out.println(result);

        modelAndView.setViewName("common/msg");
        return modelAndView;
    }
}
