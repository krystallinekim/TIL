package com.beyond.university.department.model.dto;

import com.beyond.university.department.model.vo.Department;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@ToString
public class DepartmentsResponseDto {
    private final int code;
    private final String message;
    private final List<Department> items;
    private final int page;
    private final int numOfRows;
    private final int totalCount;

    public DepartmentsResponseDto(HttpStatus httpStatus, List<Department> items, int page, int totalCount) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.items = items;
        this.page = page;
        this.numOfRows = items.size();
        this.totalCount = totalCount;
    }
}
