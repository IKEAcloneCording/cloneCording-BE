package com.innovation.backend.controller;

import com.innovation.backend.dto.request.SignupRequestDto;
import com.innovation.backend.dto.response.ResponseDto;
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

    //회원가입 - 모두 접근 가능
    @PostMapping("/api/signup")
    public ResponseDto<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }
}
