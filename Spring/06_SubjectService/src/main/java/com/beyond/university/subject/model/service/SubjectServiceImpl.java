package com.beyond.university.subject.model.service;

import com.beyond.university.subject.model.mapper.SubjectMapper;
import com.beyond.university.subject.model.vo.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectMapper subjectMapper;

    @Override
    public Optional<Subject> getSubjectBySubjectNo(String subjectNo) {

        return subjectMapper.selectSubjectSubjectNo(subjectNo);
    }
}
