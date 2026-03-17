package com.beyond.university.student.model.vo;

import com.beyond.university.department.model.vo.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String no;

    private String departmentNo;

    private Department department;

    private String name;

    private String ssn;

    private String address;

    private LocalDate entranceDate;

    private String absenceYn;

    private String coachProfessorNo;
}
