package com.innovation.backend.service;

import com.innovation.backend.dto.request.LoginRequestDto;
import com.innovation.backend.dto.request.SignupRequestDto;
import com.innovation.backend.dto.response.MemberResponseDto;
import com.innovation.backend.dto.response.MessageResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.RefreshToken;
import com.innovation.backend.exception.*;
import com.innovation.backend.jwt.util.JwtUtil;
import com.innovation.backend.jwt.util.TokenProperties;
import com.innovation.backend.repository.MemberRepository;
import com.innovation.backend.repository.RefreshTokenRepository;
import com.innovation.backend.security.user.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

        if (!emailStrCheck(username)) {throw new InvalidUsernameException(ErrorCode.INVALID_EMAIL);}

        if (!emailDuplicateCheck(username)) {throw new DuplicateUsernameException(ErrorCode.DUPLICATE_EMAIL);}

        if (!passwordStrCheck(password)) {throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD);}

        if (!phoneNumberStrCheck(phoneNumber)) {throw new InvalidPhoneNumberException(ErrorCode.INVALID_PHONE_NUMBER);}

        if (nullCheck(name)) {throw new EmptyValueException(ErrorCode.EMPTY_VALUE);}

        if (nullCheck(address)) {throw new EmptyValueException(ErrorCode.EMPTY_VALUE);}

        else {

            Member.MemberBuilder builder = Member.builder();
            builder.username(username);
            builder.password(passwordEncoder.encode(password));
            builder.name(name);
            builder.phoneNumber(phoneNumber);
            builder.address(address);
            Member member = builder
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

    @Transactional
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String username = loginRequestDto.getEmail();
        Member member = isPresentMemberByUsername(username);

        if(member == null){ throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);}

        if(!member.validatePassword(passwordEncoder,loginRequestDto.getPassword())){
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 토큰 발급
        String accessToken = jwtUtil.createToken(username,TokenProperties.AUTH_HEADER);
        String refreshToken = jwtUtil.createToken(username, TokenProperties.REFRESH_HEADER);

        RefreshToken refreshTokenFromDB = jwtUtil.getRefreshTokenFromDB(member);

        // 로그인 경력 있는 user (DB에 Refresh Token 있음) -> 새로 로그인 했으면 새로 발급받는 토큰으로 변경
        // 로그인 처음 하는 user (DB에 Refresh Token 없음) -> 발급받은 Refresh 토큰 저장
        if(refreshTokenFromDB == null){
            RefreshToken saveRefreshToken = RefreshToken.builder()
                    .member(member)
                    .tokenValue(refreshToken)
                    .build();
            refreshTokenRepository.save(saveRefreshToken);
        }else{
            refreshTokenFromDB.updateValue(refreshToken);
        }

        // 헤더에 응답으로 보내줌
        TokenToHeaders(response, accessToken, refreshToken);

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getUsername())
                .name(member.getName())
                .build();
        return ResponseDto.success(memberResponseDto);
    }

    @Transactional
    public ResponseDto<?> logout(HttpServletRequest request, UserDetailsImpl userDetails){

        Member member = userDetails.getMember();

        String refreshHeader = request.getHeader(TokenProperties.REFRESH_HEADER);

        if(refreshHeader == null){throw new NeedRefreshTokenException(ErrorCode.NEED_REFRESH_TOKEN);}

        if(!refreshHeader.startsWith(TokenProperties.TOKEN_TYPE)){
            throw new InvalidRefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String refreshToken = refreshHeader.replace(TokenProperties.TOKEN_TYPE,"");

        // 토큰 검증
        String refreshTokenValidate = jwtUtil.validateToken(refreshToken);

        switch (refreshTokenValidate) {
            case TokenProperties.VALID:
            case TokenProperties.EXPIRED:
                RefreshToken refreshTokenFromDB = jwtUtil.getRefreshTokenFromDB(member);
                if (refreshTokenFromDB != null && refreshToken.equals(refreshTokenFromDB.getTokenValue())) {
                    refreshTokenRepository.delete(refreshTokenFromDB);
                    MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                            .message("로그아웃 되었습니다.")
                            .build();
                    return ResponseDto.success(messageResponseDto);
                } else {
                    throw new InvalidRefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN);
                }
            default:
                throw new InvalidRefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    // 회원가입 조건 검증
    private boolean emailDuplicateCheck(String email){
        Object member = isPresentMemberByUsername(email);
        return member == null;
    }

    private boolean phoneNumberStrCheck (String phoneNumber){
        return Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumber);
    }

    // 회원가입, 로그인 조건 검증
    public boolean nullCheck(String valueForSignup){
        return valueForSignup == null || valueForSignup.equals("") || valueForSignup.equals(" ");
    }

    private boolean emailStrCheck (String email){
//        return Pattern.matches("^[a-zA-Z0-9]{1,64}+@[a-zA-Z0-9]{1,100}+$", email);
        return Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){1,64}@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", email);
    }

    private boolean passwordStrCheck (String password){
        return Pattern.matches("^(?=.*\\d)[A-Z\\d!@#$%^&*]{8,}$", password);
    }

    @Transactional(readOnly = true)
    public Member isPresentMemberByUsername(String username) {
        Optional<Object> optionalMember = memberRepository.findByUsername(username);
        return (Member) optionalMember.orElse(null);
    }

    private void TokenToHeaders(HttpServletResponse response, String accessToken, String refreshToken) {
        response.addHeader(TokenProperties.AUTH_HEADER, TokenProperties.TOKEN_TYPE + accessToken);
        response.addHeader(TokenProperties.REFRESH_HEADER, TokenProperties.TOKEN_TYPE + refreshToken);
    }
}
