package com.innovation.backend.security.user;

import com.innovation.backend.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    @Autowired
    UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("getMember()")
    @Test
    void getMember() {
        // when
        Member presentUser = Member.builder()
                .username("presentEmail@google.com")
                .password("12345678")
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .address("테스트 주소")
                .build();

        // given
        Member resultMember = userDetails.getMember();

        // then
        Assertions.assertThat(resultMember).isNotNull();
        Assertions.assertThat(resultMember.getName()).isEqualTo("홍길동");
    }

    @Test
    void setMember() {
    }

    @Test
    void getAuthorities() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void getUsername() {
    }

    @Test
    void isAccountNonExpired() {
    }

    @Test
    void isAccountNonLocked() {
    }

    @Test
    void isCredentialsNonExpired() {
    }

    @Test
    void isEnabled() {
    }
}