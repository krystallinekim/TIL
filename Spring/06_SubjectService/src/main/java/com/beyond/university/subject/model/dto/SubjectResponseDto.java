package com.beyond.university.subject.model.dto;

import com.beyond.university.subject.model.vo.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class SubjectResponseDto {
	@Schema(description = "응답 코드", example = "200")
	private final int code;

    @Schema(description = "응답 메시지", example = "OK")
    private final String message;

    @Schema(description = "응답 데이터")
    private final List<Subject> result;
    
	public SubjectResponseDto(HttpStatus status, Subject subject) {
		this.code = status.value();
		this.message = status.getReasonPhrase();
		this.result = Collections.singletonList(subject);
	}
}
