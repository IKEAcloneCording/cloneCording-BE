package com.innovation.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "IKEA 클론코딩 API명세서",
        description = "상품 조회 , 상품 검색, 회원가입, 로그인, 장바구니 CRUD API 명세서",
        version = "v1",
        contact = @Contact(
            name = "이노베이션 캠프 7조",
            url = "https://www.notion.so/7-IKEA-081fb84cc30747b981c8720a035b48fb"
        )
    )
)
//todo: SecuritySchemes
@Configuration
public class OpenApiConfig {

//
//    @Bean
//    public GroupedOpenApi sampleGroupOpenApi() {
//        String[] paths = {"/member/**"};
//
//        return GroupedOpenApi.builder().group("샘플 멤버 API").pathsToMatch(paths)
//            .build();
//    }

}