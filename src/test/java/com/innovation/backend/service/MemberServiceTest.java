package com.innovation.backend.service;

import com.innovation.backend.dto.request.SignupRequestDto;
import com.innovation.backend.dto.response.MemberResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

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
        void signupNormalTest() {
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
        void isPresentMemberByEmail() {
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