package com.innovation.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "로그인 요청DTO")
@Getter
@Builder
@AllArgsConstructor
public class LoginRequestDto {
    @Schema(description = "email주소", example = "test1@google.com")
    private String email;
    @Schema(description = "비밀번호", example = "12345678")
    private String password;
}
