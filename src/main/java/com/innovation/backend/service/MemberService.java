package com.innovation.backend.service;

import com.innovation.backend.dto.request.SignupRequestDto;
import com.innovation.backend.dto.response.MemberResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.jwt.util.JwtUtil;
import com.innovation.backend.jwt.util.TokenProperties;
import com.innovation.backend.repository.MemberRepository;
import com.innovation.backend.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //회원가입
    @Transactional
    public ResponseDto<?> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        String name = signupRequestDto.getName();
        String phoneNumber = signupRequestDto.getPhone_number();
        String address = signupRequestDto.getAddress();

        if (!emailStrCheck(username)) {return ResponseDto.fail(ErrorCode.INVALID_EMAIL);}

        if (!emailDuplicateCheck(username)) {return ResponseDto.fail(ErrorCode.DUPLICATE_EMAIL);}

        if (!passwordStrCheck(password)) {return ResponseDto.fail(ErrorCode.INVALID_PASSWORD);}

        if (!phoneNumberStrCheck(phoneNumber)) {return ResponseDto.fail(ErrorCode.INVALID_PHONE_NUMBER);}

        if (nullCheck(name)) {return ResponseDto.fail(ErrorCode.EMPTY_VALUE);}

        if (nullCheck(address)) {return ResponseDto.fail(ErrorCode.EMPTY_VALUE);}

        else {

            Member member = Member.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .build();

            memberRepository.save(member);

            MemberResponseDto signupInfo = MemberResponseDto.builder()
                    .id(member.getId())
                    .email(member.getUsername())
                    .name(member.getName())
                    .build();

            return ResponseDto.success(signupInfo);
        }
    }

    // 회원가입 조건 검증
    private boolean emailDuplicateCheck(String email){
        Object member = isPresentMemberByEmail(email);
        return member == null;
    }

    public boolean nullCheck(String valueForSignup){
        return valueForSignup == null || valueForSignup.equals("") || valueForSignup.equals(" ");
    }

    private boolean emailStrCheck (String email){
//        return Pattern.matches("^[a-zA-Z0-9]{1,64}+@[a-zA-Z0-9]{1,100}+$", email);
        return Pattern.matches("^[a-zA-Z0-9]+@[a-z]+.[a-z]+$", email);
    }

    private boolean passwordStrCheck (String password){
        return Pattern.matches("^[A-Z0-9]{8,}$", password);
    }

    private boolean phoneNumberStrCheck (String phoneNumber){
        return Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumber);
    }

    @Transactional(readOnly = true)
    public Object isPresentMemberByEmail(String email) {
        Optional<Object> optionalMember = memberRepository.findByUsername(email);
        return optionalMember.orElse(null);
    }

    private void TokenToHeaders(HttpServletResponse response, String accessToken, String refreshToken) {
        response.addHeader(TokenProperties.AUTH_HEADER, TokenProperties.TOKEN_TYPE + accessToken);
        response.addHeader(TokenProperties.REFRESH_HEADER, TokenProperties.TOKEN_TYPE + refreshToken);
    }
}
