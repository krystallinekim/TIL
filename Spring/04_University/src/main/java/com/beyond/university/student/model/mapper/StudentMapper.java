package com.beyond.university.student.model.mapper;

import com.beyond.university.student.model.vo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> selectAllByDepartmentNo(@Param("dno") String dno);

    Student selectStudentByNo(@Param("sno") String sno);

    int insertStudent(Student student);
}
