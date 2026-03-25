package com.beyond.university.subject.model.service;

import com.beyond.university.subject.model.vo.Subject;

import java.util.Optional;

public interface SubjectService {
    Optional<Subject> getSubjectBySubjectNo(String subjectNo);
}
