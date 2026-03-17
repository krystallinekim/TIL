package com.beyond.university.student.controller;

import com.beyond.university.department.model.dto.DepartmentsDto;
import com.beyond.university.department.model.service.DepartmentService;
import com.beyond.university.department.model.vo.Department;
import com.beyond.university.student.model.dto.StudentAddRequestDto;
import com.beyond.university.student.model.dto.StudentUpdateRequestDto;
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

        System.out.println();

        // 연관관계 매핑 - 학생 조회시 학과이름, 카테고리도 같이 조회
        // System.out.println(student);
        // System.out.println(student.getDepartment());


        // 연관관계 매핑 - 특정 학과번호 조회 시 해당하는 학생들 조회

        Department department = departmentService.getDepartmentByNo(student.getDepartmentNo());

        department.getStudents().forEach(System.out::println);

        System.out.println();

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

        log.info("Student No : {}", student.getNo());

        result = studentService.save(student);

        if (result > 0) {
            modelAndView.addObject("msg", "학생이 등록되었습니다");
            modelAndView.addObject("location", "/student/info?sno=" + student.getNo());
        } else {
            modelAndView.addObject("msg", "등록에 실패했습니다");
            modelAndView.addObject("location", "/student/add");
        }

        System.out.println(result);

        modelAndView.setViewName("common/msg");
        return modelAndView;
    }

    @PostMapping("student/update")
    public ModelAndView update(ModelAndView modelAndView, StudentUpdateRequestDto studentUpdateRequestDto) {

        Student student = studentUpdateRequestDto.toStudent();

        int result = studentService.save(student);

        if (result > 0) {
            modelAndView.addObject("msg", "정보가 수정되었습니다");
        } else {
            modelAndView.addObject("msg", "정보 수정을 실패했습니다");
        }
        modelAndView.addObject("location", "/student/info?sno=" + student.getNo());


        modelAndView.setViewName("common/msg");

        return modelAndView;
    }

    @PostMapping("student/delete")
    public ModelAndView delete(ModelAndView modelAndView, String sno) {

        log.info("Student No : {}", sno);

        int result = studentService.delete(sno);

        if (result > 0) {
            modelAndView.addObject("msg", "삭제되었습니다");
            modelAndView.addObject("location", "/student/search");
        } else {
            modelAndView.addObject("msg", "삭제를 실패했습니다");
            modelAndView.addObject("location", "/student/info?sno=" + sno);
        }

        modelAndView.setViewName("common/msg");

        return modelAndView;
    }
}
