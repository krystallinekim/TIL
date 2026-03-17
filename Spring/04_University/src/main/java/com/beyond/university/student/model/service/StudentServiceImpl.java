package com.beyond.university.student.model.service;

import com.beyond.university.student.model.mapper.StudentMapper;
import com.beyond.university.student.model.vo.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;

    @Override
    public List<Student> getStudentsByDepartmentNo(String dno) {

        return studentMapper.selectAllByDepartmentNo(dno);
    }

    @Override
    public Student getStudentByNo(String sno) {

        return studentMapper.selectStudentByNo(sno);
    }

    @Transactional
    @Override
    public int save(Student student) {
        int result = 0;

        if (student.getNo() != null) {
            // update
        } else {
            // insert
            result = studentMapper.insertStudent(student);
        }


        return result;
    }
}
