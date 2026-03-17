package com.beyond.university.student.model.dto;

import com.beyond.university.student.model.vo.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@RequiredArgsConstructor
public class StudentAddRequestDto {
    private final String departmentNo;

    private final String name;

    private final String ssn;

    private final String address;

    private final LocalDate entranceDate;

    public Student toStudent() {

        return Student.builder()
                .departmentNo(departmentNo)
                .name(name)
                .ssn(ssn)
                .address(address)
                .entranceDate(entranceDate)
                .build();
    }
}
