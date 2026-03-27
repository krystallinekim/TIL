package com.beyond.university.api;

import com.beyond.university.department.model.dto.DepartmentResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import lombok.RequiredArgsConstructor;

@Component
// 애플리케이션 실행 중에 일부 설정을 변경하도록 허용하는 어노테이션이다.
@RefreshScope
@RequiredArgsConstructor
public class DepartmentApiClient {
	private final RestTemplate restTemplate;
	@Value("${department-service.base-url}")
	private String baseUrl;
	
	public ResponseEntity<DepartmentResponseDto> getDepartmentByDeptNo(String deptNo) {
		ResponseEntity<DepartmentResponseDto> response = restTemplate.exchange(
			baseUrl + deptNo, 
			HttpMethod.GET,
			null,
			DepartmentResponseDto.class
		);
				
		return response;
	}
}
