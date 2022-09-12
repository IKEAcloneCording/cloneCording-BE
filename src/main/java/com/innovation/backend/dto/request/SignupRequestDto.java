package com.innovation.backend.dto.request;

import com.innovation.backend.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone_number;
    private String address;

    // 테스트용 메소드
    public Member toEntity() {
        return Member.builder().id(1L).username(email).password(password).name(name).phoneNumber(phone_number).address(address).build();
    }
}