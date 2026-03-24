package com.beyond.university.subject.controller;


import com.beyond.university.api.DepartmentApiClient;
import com.beyond.university.exception.UniversityException;
import com.beyond.university.exception.message.ExceptionMessage;
import com.beyond.university.department.model.dto.DepartmentResponseDto;
import com.beyond.university.subject.model.dto.SubjectResponseDto;
import com.beyond.university.subject.model.service.SubjectService;
import com.beyond.university.subject.model.vo.Subject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    과목 서비스(subject-service)
    1) 과목 상세 조회
        - GET /api/v1/subject-service/subjects/{subject-no}
    2) 과목의 학과 정보 조회
        - GET /api/v1/subject-service/subjects/{subject-no}/department
*/

@RestController
@RequestMapping("/api/v1/subject-service/subjects")
@RequiredArgsConstructor
@Tag(name = "Subject APIs", description = "과목 관련 API 목록")
public class SubjectController {
    private final SubjectService subjectService;
    private final DepartmentApiClient departmentApiClient;

    @GetMapping("/{subject-no}")
    @Operation(summary = "과목 상세 조회", description = "과목 번호로 과목의 상세 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @Parameter(name = "subject-no", description = "과목 번호", example = "C0003400")
    public ResponseEntity<SubjectResponseDto> getSubjectByNo(@PathVariable("subject-no") String subjectNo) {
        Subject subject = subjectService.getSubjectBySubjectNo(subjectNo)
                .orElseThrow(() -> new UniversityException(ExceptionMessage.SUBJECT_NOT_FOUND));

        return ResponseEntity.ok(new SubjectResponseDto(HttpStatus.OK, subject));
    }

    @GetMapping("/{subject-no}/department")
    @Operation(summary = "과목의 학과 정보 조회", description = "과목 번호로 과목의 학과 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @Parameter(name = "subject-no", description = "과목 번호", example = "C0003400")
    public ResponseEntity<DepartmentResponseDto> getDepartmentBydeptNo(
            @PathVariable("subject-no") String subjectNo) {

        Subject subject = subjectService.getSubjectBySubjectNo(subjectNo).
                orElseThrow(() -> new UniversityException(ExceptionMessage.SUBJECT_NOT_FOUND));

        return departmentApiClient.getDepartmentByDeptNo(subject.getDeptNo());
    }
}
