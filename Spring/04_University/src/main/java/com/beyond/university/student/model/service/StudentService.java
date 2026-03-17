package com.beyond.university.student.model.service;

import com.beyond.university.student.model.vo.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudentsByDepartmentNo(String dno);

    Student getStudentByNo(String sno);
}
