package com.beyond.university.department.model.dto;

import com.beyond.university.department.model.vo.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DepartmentResponseDto {
	private int code;

    private String message;

    private List<Department> items;
}
