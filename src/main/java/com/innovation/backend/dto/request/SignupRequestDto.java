package com.innovation.backend.dto.request;

import com.innovation.backend.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "회원가입 요청DTO")
@Getter
@Builder
@AllArgsConstructor
public class SignupRequestDto {
    @Schema(description = "email주소", example = "test1@google.com")
    private String email;
    @Schema(description = "비밀번호", example = "12345678")
    private String password;
    @Schema(description = "이름", example = "홍길동")
    private String name;
    @Schema(description = "핸드폰 번호", example = "010-1234-5678")
    private String phone_number;
    @Schema(description = "주소", example = "서울시 성동구 왕십리로 00-00")
    private String address;

    // 테스트용 메소드
    public Member toEntity() {
        return Member.builder().id(1L).username(email).password(password).name(name).phoneNumber(phone_number).address(address).build();
    }
}