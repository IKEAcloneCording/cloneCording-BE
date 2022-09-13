package com.innovation.backend.service;

import com.innovation.backend.dto.request.LoginRequestDto;
import com.innovation.backend.dto.request.SignupRequestDto;
import com.innovation.backend.dto.response.MemberResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.RefreshToken;
import com.innovation.backend.jwt.util.JwtUtil;
import com.innovation.backend.repository.MemberRepository;
import com.innovation.backend.repository.RefreshTokenRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
    }

    @Nested
    @DisplayName("회원가입")
    class SignupTest {

        @DisplayName("일반적인 경우")
        @Test
        void NormalsignupTest() {
            // given
            SignupRequestDto signupRequest = SignupRequestDto.builder()
                    .email("test@google.com")
                    .password("12345678")
                    .name("홍길동")
                    .phone_number("010-1234-5678")
                    .address("테스트 주소")
                    .build();
            when(memberRepository.save(any())).thenReturn(signupRequest.toEntity());

            // when
            ResponseDto signupResponse = memberService.signup(signupRequest);
            MemberResponseDto signupResultData = (MemberResponseDto) signupResponse.getData();

            // then
            Assertions.assertThat(signupResponse).isNotNull();
            Assertions.assertThat(signupResponse.isSuccess()).isEqualTo(true);
            Assertions.assertThat(signupResponse.getError()).isNull();
            Assertions.assertThat(signupResultData.getName()).isEqualTo("홍길동");
            Assertions.assertThat(signupResultData.getEmail()).isEqualTo("test@google.com");
        }

        @DisplayName("입력값이 null인 경우")
        @Test
        void signupNullTest() {
            // given
            SignupRequestDto signupRequest = SignupRequestDto.builder()
                    .email("test@google.com")
                    .password("12345678")
                    .name("")
                    .phone_number("010-1234-5678")
                    .address("테스트 주소")
                    .build();
            when(memberRepository.save(any())).thenReturn(signupRequest.toEntity());

            // when
            ResponseDto signupResponse = memberService.signup(signupRequest);
            MemberResponseDto signupResultData = (MemberResponseDto) signupResponse.getData();

            // then
            Assertions.assertThat(signupResponse).isNotNull();
            Assertions.assertThat(signupResponse.isSuccess()).isEqualTo(false);
            Assertions.assertThat(signupResponse.getError().getCode()).isEqualTo("EMPTY_VALUE");
        }

        @DisplayName("중복 email일 경우")
        @Test
        void signupDuplicateTest() {
            // given
            SignupRequestDto signupRequest = SignupRequestDto.builder()
                    .email("presentEmail@google.com")
                    .password("12345678")
                    .name("")
                    .phone_number("010-1234-5678")
                    .address("테스트 주소")
                    .build();

            String presentUsername = "presentEmail@google.com";
            Member presentUser = Member.builder()
                    .username("presentEmail@google.com")
                    .password("12345678")
                    .name("홍길동")
                    .phoneNumber("010-1234-5678")
                    .address("테스트 주소")
                    .build();
            when(memberRepository.findByUsername(presentUsername)).thenReturn(Optional.ofNullable(presentUser));

            // when
            ResponseDto signupResponse = memberService.signup(signupRequest);
            MemberResponseDto signupResultData = (MemberResponseDto) signupResponse.getData();

            // then
            Assertions.assertThat(signupResponse).isNotNull();
            Assertions.assertThat(signupResponse.isSuccess()).isEqualTo(false);
            Assertions.assertThat(signupResponse.getError().getCode()).isEqualTo("DUPLICATE_EMAIL");
        }

        @DisplayName("유효하지 않은 이메일의 경우")
        @Test
        void signupInvalidEmailTest() {
            // given
            SignupRequestDto signupRequest = SignupRequestDto.builder()
                    .email("test")
                    .password("12345678")
                    .name("홍길동")
                    .phone_number("010-1234-5678")
                    .address("테스트 주소")
                    .build();
            when(memberRepository.save(any())).thenReturn(signupRequest.toEntity());

            // when
            ResponseDto signupResponse = memberService.signup(signupRequest);
            MemberResponseDto signupResultData = (MemberResponseDto) signupResponse.getData();

            // then
            Assertions.assertThat(signupResponse).isNotNull();
            Assertions.assertThat(signupResponse.isSuccess()).isEqualTo(false);
            Assertions.assertThat(signupResponse.getError().getCode()).isEqualTo("INVALID_EMAIL");
        }

        @DisplayName("유효하지 않은 비밀번호의 경우")
        @Test
        void signupInvalidPWTest() {
            // given
            SignupRequestDto signupRequest = SignupRequestDto.builder()
                    .email("test@gmail.com")
                    .password("1234")
                    .name("홍길동")
                    .phone_number("010-1234-5678")
                    .address("테스트 주소")
                    .build();
            when(memberRepository.save(any())).thenReturn(signupRequest.toEntity());

            // when
            ResponseDto signupResponse = memberService.signup(signupRequest);
            MemberResponseDto signupResultData = (MemberResponseDto) signupResponse.getData();

            // then
            Assertions.assertThat(signupResponse).isNotNull();
            Assertions.assertThat(signupResponse.isSuccess()).isEqualTo(false);
            Assertions.assertThat(signupResponse.getError().getCode()).isEqualTo("INVALID_PASSWORD");
        }

        @DisplayName("유효하지 않은 휴대폰 번호")
        @Test
        void signupInvalidPhoneNumberTest() {
            // given
            SignupRequestDto signupRequest = SignupRequestDto.builder()
                    .email("test@gmail.com")
                    .password("12345678")
                    .name("홍길동")
                    .phone_number("010-")
                    .address("테스트 주소")
                    .build();
            when(memberRepository.save(any())).thenReturn(signupRequest.toEntity());

            // when
            ResponseDto signupResponse = memberService.signup(signupRequest);
            MemberResponseDto signupResultData = (MemberResponseDto) signupResponse.getData();

            // then
            Assertions.assertThat(signupResponse).isNotNull();
            Assertions.assertThat(signupResponse.isSuccess()).isEqualTo(false);
            Assertions.assertThat(signupResponse.getError().getCode()).isEqualTo("INVALID_PHONE_NUMBER");
        }
    }

    @Nested
    @DisplayName("로그인")
    class LoginTest {

        @DisplayName("로그인 성공")
        @Test
        void loginSuccess() {
            // given
            HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

            LoginRequestDto loginRequest = LoginRequestDto.builder()
                    .email("presentEmail@google.com")
                    .password("12345678")
                    .build();

            Member presentUser = Member.builder()
                    .username("presentEmail@google.com")
                    .password("12345678")
                    .name("홍길동")
                    .phoneNumber("010-1234-5678")
                    .address("테스트 주소")
                    .build();

            when(memberRepository.findByUsername("test@google.com")).thenReturn(Optional.ofNullable(presentUser));
            when(passwordEncoder.matches(any(), any())).thenReturn(true);
            when(jwtUtil.createToken("presentEmail@google.com", "Authorization")).thenReturn("mockAccessToken");
            when(jwtUtil.createToken("presentEmail@google.com", "Refresh-Token")).thenReturn("mockRefreshToken");

            RefreshToken refreshTokenFromDB = RefreshToken.builder()
                    .member(presentUser)
                    .tokenValue("mockTokenValue")
                    .build();
            when(jwtUtil.getRefreshTokenFromDB(presentUser)).thenReturn(refreshTokenFromDB);
            when(refreshTokenRepository.save(any())).thenReturn(Optional.ofNullable(presentUser));

            // when
            ResponseDto<?> loginResponse = memberService.login(loginRequest, httpServletResponse);
            MemberResponseDto loginResultData = (MemberResponseDto) loginResponse.getData();

            // then
            Assertions.assertThat(loginResponse).isNotNull();
            Assertions.assertThat(loginResponse.isSuccess()).isEqualTo(true);
            Assertions.assertThat(loginResponse.getError()).isNull();
            Assertions.assertThat(loginResultData.getName()).isEqualTo("홍길동");
            Assertions.assertThat(loginResultData.getEmail()).isEqualTo("presentEmail@google.com");
        }

        @DisplayName("이메일 오류")
        @Test
        void loginWrongEmail() {

            // given
            HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

            LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                    .email("test")
                    .password("12345678")
                    .build();

            // when
            ResponseDto<?> loginResponse = memberService.login(loginRequestDto, httpServletResponse);

            // then
            Assertions.assertThat(loginResponse).isNotNull();
            Assertions.assertThat(loginResponse.isSuccess()).isEqualTo(false);
            Assertions.assertThat(loginResponse.getError().getCode()).isEqualTo("MEMBER_NOT_FOUND");
        }

        @DisplayName("패스워드 오류")
        @Test
        void loginWrongPassword() {

            // given
            HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

            LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                    .email("test@google.com")
                    .password("1234")
                    .build();

            // when
            ResponseDto<?> loginResponse = memberService.login(loginRequestDto, httpServletResponse);

            // then
            Assertions.assertThat(loginResponse).isNotNull();
            Assertions.assertThat(loginResponse.isSuccess()).isEqualTo(false);
            Assertions.assertThat(loginResponse.getError().getCode()).isEqualTo("MEMBER_NOT_FOUND");
        }
    }

    @Nested
    @DisplayName("공통 기능")
    class SignupCommonFunctionTest {
        @DisplayName("null값 확인하기")
        @Test
        void nullCheck() {
            // given

            // when
            boolean notNullResult = memberService.nullCheck("abcd");
            boolean nullResult = memberService.nullCheck("");

            // then
            Assertions.assertThat(notNullResult).isEqualTo(false);
            Assertions.assertThat(nullResult).isEqualTo(true);
        }

        @DisplayName("이메일로 이미 있는 회원인지 확인하기")
        @Test
        void isPresentMemberByUsername() {
            // given
            String presentUsername = "presentEmail@google.com";
            Member presentUser = Member.builder()
                    .username("presentEmail@google.com")
                    .password("12345678")
                    .name("홍길동")
                    .phoneNumber("010-1234-5678")
                    .address("테스트 주소")
                    .build();
            when(memberRepository.findByUsername(presentUsername)).thenReturn(Optional.ofNullable(presentUser));

            // when
            Object presentUsernameResult = memberService.isPresentMemberByUsername("presentEmail@google.com");

            // then
            Assertions.assertThat(presentUsername).isNotNull();
        }
    }
}