package com.innovation.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 응답DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    @Schema(description = "사용자 id (PK)", example = "1")
    private Long id;
    @Schema(description = "email주소", example = "test1@google.com")
    private String email;
    @Schema(description = "이름", example = "홍길동")
    private String name;
}
