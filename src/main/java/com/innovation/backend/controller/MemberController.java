package com.innovation.backend.controller;

import com.innovation.backend.dto.request.LoginRequestDto;
import com.innovation.backend.dto.request.SignupRequestDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.security.user.UserDetailsImpl;
import com.innovation.backend.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/api/signup")
    public ResponseDto<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    // 로그아웃
    @PostMapping("/api/auth/logout")
    public ResponseDto<?> logout(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.logout(request, userDetails);
    }
}
