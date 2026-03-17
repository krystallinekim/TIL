package com.beyond.university.department.model.vo;

import com.beyond.university.student.model.vo.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private String no;
    private String name;
    private String category;
    private String openYn;
    private int capacity;
    private List<Student> students;
}
