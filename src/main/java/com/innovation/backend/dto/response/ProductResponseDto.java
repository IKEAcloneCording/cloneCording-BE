package com.innovation.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "상품 응답DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    @Schema(description = "상품 id (PK)", example = "29")
    private Long id;
    @Schema(description = "상품명", example = "ANGERSBY 앙에르스뷔")
    private String name;
    @Schema(description = "상품 상세", example = "2인용소파")
    private String description;
    @Schema(description = "상품 측정 사이즈", example = "null")
    private String measurement;
    @Schema(description = "상품 가격", example = "229000")
    private BigDecimal price;
    @Schema(description = "상품 이미지", example = "https://www.ikea.com/kr/ko/images/products/angersby-2-seat-sofa-knisa-light-grey__0770896_pe755642_s5.jpg?f=xxs")
    private String image_url;
    @Schema(description = "상품 이미지2", example = "https://www.ikea.com/kr/ko/images/products/angersby-2-seat-sofa-knisa-light-grey__0944427_pe797252_s5.jpg?f=xxs")
    private String subImage_url;
    @Schema(description = "url", example = "/")
    private String url;
}
