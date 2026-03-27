package com.beyond.university.subject.model.mapper;

import com.beyond.university.subject.model.vo.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface SubjectMapper {
    Optional<Subject> selectSubjectSubjectNo(@Param("subjectNo") String subjectNo);
}
