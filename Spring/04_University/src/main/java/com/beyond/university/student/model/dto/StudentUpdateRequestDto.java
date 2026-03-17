package com.beyond.university.student.model.dto;

import com.beyond.university.student.model.vo.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class StudentUpdateRequestDto {
    private final String no;

    private final String departmentNo;

    private final String name;

    private final String ssn;

    private final String address;

    private final String absenceYn;

    public Student toStudent() {

        return Student.builder()
                .no(no)
                .departmentNo(departmentNo)
                .name(name)
                .ssn(ssn)
                .address(address)
                .absenceYn(absenceYn)
                .build();
    }
}
