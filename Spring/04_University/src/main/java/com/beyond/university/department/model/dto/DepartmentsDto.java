package com.beyond.university.department.model.dto;

import com.beyond.university.department.model.vo.Department;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DepartmentsDto {
    private final String no;

    private final String name;

    public DepartmentsDto(Department department) {
        this.no = department.getNo();
        this.name = department.getName();
    }
}
