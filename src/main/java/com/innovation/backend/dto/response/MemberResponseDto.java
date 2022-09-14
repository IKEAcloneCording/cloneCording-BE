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
    @Schema(description = "사용자 id (PK)")
    private Long id;
    @Schema(description = "이메일 주소")
    private String email;
    @Schema(description = "사용자 이름")
    private String name;
}
