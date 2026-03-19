package com.beyond.university.department.controller;

import com.beyond.university.department.model.dto.DepartmentsResponseDto;
import com.beyond.university.department.model.service.DepartmentService;
import com.beyond.university.department.model.vo.Department;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/*
    학과 관련 API

    1. 학과 목록 조회
      - GET /api/v1/department-service/departments

    2. 학과 상세 조회
      - GET /api/v1/department-service/departments/{department-no}  // department: 001~063

    3. 학과 등록
      - POST /api/v1/department-service/departments

    4. 학과 수정
      - PUT /api/v1/department-service/departments/{department-no}

    5. 학과 삭제
      - DELETE /api/v1/department-service/departments/{department-no}
 */

@RestController
@RequestMapping("/api/v1/department-service")
@RequiredArgsConstructor
@Tag(name="Department APIs", description = "학과 관련 API 목록")  // 컨트롤러 전반적인 설명
public class DepartmentController {

    private final DepartmentService departmentService;
    @GetMapping("/departments")
    @Operation(summary = "목록 조회", description = "학과의 목록을 조회한다.")  // API에 대한 설명
    @Parameters({  // 주어진 파라미터에 대한 설명 - 파라미터 변수명과 name이 같아야 함
        @Parameter(name="page", description = "페이지 번호", example = "1"),
        @Parameter(name="numOfRows", description = "한 페이지 결과 수", example = "10")
    })
    // @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<DepartmentsResponseDto> getDepartments(
            @RequestParam int page,
            @RequestParam int numOfRows
    ) {

        int totalCount = departmentService.getDepartmentsCount();

        List<Department> departments = departmentService.getDepartments(page, numOfRows);

        System.out.println(totalCount);

        return ResponseEntity.ok(new DepartmentsResponseDto(HttpStatus.OK, departments, page, totalCount));
    }



}
