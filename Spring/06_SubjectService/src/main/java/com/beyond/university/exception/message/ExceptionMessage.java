package com.beyond.university.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    DEPARTMENT_NOT_FOUND("학과 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SUBJECT_NOT_FOUND("과목 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);;

    private final String message;

    private final HttpStatus status;
}
